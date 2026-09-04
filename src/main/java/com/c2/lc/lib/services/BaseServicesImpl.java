package com.c2.lc.lib.services;


import com.c2.lc.lib.base.BaseSuper;
import com.c2.lc.lib.kafka.KafkaHelper;
import com.c2.lc.lib.security.AesCbcEncryption;
import com.c2.lc.lib.services.interfaces.BaseService;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions;
import com.microsoft.azure.storage.blob.SharedAccessBlobPolicy;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;

public class BaseServicesImpl extends BaseSuper implements BaseService {

    @Autowired protected AesCbcEncryption aesCbcEncryption;
    @Autowired protected KafkaHelper kafkaHelper;

    private static WebClient.Builder webClientBuilder = null;
    private static WebClient.Builder webProxyClientBuilder = null;

    private final static int HTTP_CALL_SIZE = 16 * 1024 * 1024;

    protected WebClient.Builder getWebClient() {
        if (webClientBuilder == null) {
            webClientBuilder = WebClient.builder();
        }
        return webClientBuilder;
    }

    protected WebClient.Builder getProxyWebClient() {
        if (webProxyClientBuilder == null) {
            webProxyClientBuilder = WebClient.builder();
        }
        return webProxyClientBuilder;
    }

    protected String callWebClientPostSyncApi(String uri, Object payload, String userName, String pwd) {
        return getWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build()).build()
                .method(HttpMethod.POST)
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .block();
    }

    protected String callWebClientPostSyncApi(String uri, Object payload) {
        return getWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build()).build()
                .method(HttpMethod.POST)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .block();
    }

    protected Mono<String> callWebClientPostASyncApi(String uri, Object payload) {
        return getWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build()).build()
                .method(HttpMethod.POST)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
    }

    protected String callWebClientGetSyncApi(String uri) {
        return getWebClient().exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(config -> config
                                .defaultCodecs()
                                .maxInMemorySize(HTTP_CALL_SIZE)
                        ).build())
                .build()
                .method(HttpMethod.GET)
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    protected String callWebClientGetSyncApi(String uri, Map<String, String> headers) {
        WebClient.RequestBodySpec spec = getWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build()).build()
                .method(HttpMethod.GET)
                .uri(uri);

        if (headers != null && headers.size() > 0) {
            headers.forEach(spec::header);
        }

        return spec
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    protected String callWebClientPostSyncApiWithHeader(String uri, String payload, Map<String,String> headers) {
        return callWebClientPostSyncApiWithHeader(uri, payload, headers, true);
    }

    protected String callWebClientPostSyncApiWithHeader(String uri, String payload, Map<String,String> headers, boolean setJsonContentType) {
        WebClient.RequestBodySpec spec = getWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build())
                .build()
                .post()
                .uri(uri);

        if (headers != null && headers.size() > 0) {
            headers.forEach(spec::header);
        }

        if (setJsonContentType) {
            return spec.contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(payload))
                    .retrieve().bodyToMono(String.class).block();
        } else {
            return spec.body(BodyInserters.fromValue(payload))
                    .retrieve().bodyToMono(String.class).block();
        }
    }

    protected String callWebClientGetSyncApiWithProxyAndHeader(String uri, Map<String, String> headers, String proxyHost, int proxyPort) throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        HttpClient httpClient = HttpClient.create()
                .proxy(proxy ->
                                proxy.type(ProxyProvider.Proxy.HTTP)
                                        .host(proxyHost)
                                        .port(proxyPort)
                )
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        WebClient client = getProxyWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build()).clientConnector(new ReactorClientHttpConnector(httpClient)).build();

        WebClient.RequestBodySpec spec = client
                .method(HttpMethod.GET)
                .uri(uri);

        if (headers != null && headers.size() > 0) {
            headers.forEach(spec::header);
        }

        return spec
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    protected String callWebClientPostSyncApiWithProxyAndHeader(String uri, String payload, Map<String,String> headers, String proxyHost, int proxyPort) throws SSLException {
        return callWebClientPostSyncApiWithProxyAndHeader(uri, payload, headers, true, proxyHost, proxyPort);
    }

    protected String callWebClientPostSyncApiWithProxyAndHeader(String uri, String payload, Map<String,String> headers, boolean setJsonContentType, String proxyHost, int proxyPort) throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        HttpClient httpClient = HttpClient.create()
                .proxy(proxy ->
                                proxy.type(ProxyProvider.Proxy.HTTP)
                                        .host(proxyHost)
                                        .port(proxyPort)
                )
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        WebClient client = getProxyWebClient().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(HTTP_CALL_SIZE)
                ).build()).clientConnector(new ReactorClientHttpConnector(httpClient)).build();

        WebClient.RequestBodySpec spec = client.post().uri(uri);

        if (headers != null && headers.size() > 0) {
            headers.forEach(spec::header);
        }

        if (setJsonContentType) {
            return spec.contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(payload))
                    .retrieve().bodyToMono(String.class).block();
        } else {
            return spec.body(BodyInserters.fromValue(payload))
                    .retrieve().bodyToMono(String.class).block();
        }
    }

    @Autowired(required = false) private CloudBlobContainer cloudBlobContainer;

    protected String uploadToBlob(String path, MultipartFile file)
            throws URISyntaxException, StorageException, IOException {

        return uploadToBlob(path, file.getBytes());
    }

    protected String uploadToBlob(String path, String data)
            throws URISyntaxException, StorageException, IOException {

        return uploadToBlob(path, Base64.getMimeDecoder().decode(data));
    }

    protected String uploadToBlob(String path, byte[]  data)
            throws URISyntaxException, StorageException, IOException {

        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(path);
        blob.uploadFromByteArray(data, 0, data.length);
        URI uri = blob.getUri();

        return uri.toString();
    }

    protected String uploadToBlob(String path, InputStream data, long size)
            throws URISyntaxException, StorageException, IOException {

        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(path);
        blob.upload(data, size);
        URI uri = blob.getUri();

        return uri.toString();
    }

    protected void deleteBlob(String path)
            throws URISyntaxException, StorageException {

        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(path);
        blob.delete();
    }

    protected String getBlobSignedURI(String path)
            throws URISyntaxException, StorageException, InvalidKeyException {
        return getBlobSignedURI(path, 60);
    }

    protected String getBlobSignedURI(String path, int validTillInMinutes)
            throws URISyntaxException, StorageException, InvalidKeyException {

        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(path);
        SharedAccessBlobPolicy sasPolicy = new SharedAccessBlobPolicy();
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.MINUTE, validTillInMinutes);
        sasPolicy.setSharedAccessExpiryTime(calendar.getTime());
        sasPolicy.setPermissions(EnumSet.of(SharedAccessBlobPermissions.READ));
        String sas = blob.generateSharedAccessSignature(sasPolicy ,null);
        return String.format("%s?%s", blob.getUri(), sas);
    }

    protected boolean checkBlobExist(String path)
            throws URISyntaxException, StorageException {

        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(path);
        return blob.exists();
    }

    protected String checkFolderNameIfNotExist(String name) {
        File file = new File(name);
        if (!file.exists()) {
            return name;
        }
        return String.valueOf(file);
    }

    protected String createFolderNameIfNotExist(String name) {
        File file = new File(name);
        if (!file.exists()) {
            return String.valueOf(file.mkdirs());
        }
        return String.valueOf(file);
    }


}
