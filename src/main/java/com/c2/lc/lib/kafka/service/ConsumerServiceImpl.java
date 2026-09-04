package com.c2.lc.lib.kafka.service;

import com.c2.lc.lib.kafka.ApiRequestData;
import com.c2.lc.lib.kafka.ApiRequestTopic;
import com.c2.lc.lib.kafka.LtApiRequestTopic;
import com.c2.lc.lib.kafka.service.interfaces.ConsumerService;
import com.c2.lc.lib.services.BaseServicesImpl;
import com.google.gson.JsonObject;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerServiceImpl extends BaseServicesImpl implements ConsumerService {

    @Override
    public JsonObject callApi(String baseUrl, ApiRequestTopic topicData, int timeOut) {
        return call(
                baseUrl,
                topicData.getTopicData().getUri(),
                topicData.getTopicData().getBr_code(),
                topicData.getTopicData().getC2_code(),
                topicData.getTopicData().getData(),
                timeOut
        );
    }

    @Override
    public JsonObject callApi(String baseUrl, LtApiRequestTopic topicData, int timeOut) {
        return call(
                baseUrl,
                topicData.getTopicData().getUri(),
                topicData.getTopicData().getBr_code(),
                topicData.getTopicData().getC2_code(),
                topicData.getTopicData().getData(),
                timeOut
        );
    }

    private RestTemplate getRestTemplate(int timeOut){
        return new RestTemplate(getClientHttpRequestFactory(timeOut));
    };

    private ClientHttpRequestFactory getClientHttpRequestFactory(int timeOut) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeOut);
        factory.setReadTimeout(timeOut);
        return factory;
    }

    private JsonObject call(String baseUrl,String uri, String brCode, String c2Code, Object data, int timeOut){
        ApiRequestData request = new ApiRequestData();
        String apiUrl = String.format("%s%s", baseUrl, uri);

        request.setBr_code(brCode);
        request.setC2_code(c2Code);
        request.setData(data);

        String response = getRestTemplate(timeOut).postForObject(apiUrl, request, String.class);

        return (JsonObject) helper.fromJSON(response, JsonObject.class);
    }

}
