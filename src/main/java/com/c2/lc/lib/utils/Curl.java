package com.c2.lc.lib.utils;

import com.c2.lc.lib.exceptions.InvalidRequestException;
import com.c2.lc.lib.properties.Messages;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Curl {

	public JSONObject execute(String method, String url, String key, String secret, JSONObject data) throws IOException, InvalidRequestException, JSONException {

		String credentials = Credentials.basic(key, secret);
		MediaType JSON = MediaType.parse(Constants.APPLICATION_JSON_CHARSET_UTF_8);
		OkHttpClient client = getClient();

		Request request;
            if (method.equalsIgnoreCase(Constants.METHOD_GET)) {
                request = new Request.Builder()
                        .url(url)
                        .addHeader(Constants.AUTHORIZATION, credentials)
                        .get()
                        .build();
            } else if (method.equalsIgnoreCase(Constants.METHOD_POST)) {
                RequestBody body = RequestBody.create(JSON, data.toString());
                request = new Request.Builder()
                        .url(url)
                        .addHeader(Constants.AUTHORIZATION, credentials)
                        .post(body)
                        .build();
            } else if (method.equalsIgnoreCase(Constants.METHOD_PUT)) {
                RequestBody body = RequestBody.create(JSON, data.toString());
                request = new Request.Builder()
                        .url(url)
                        .addHeader(Constants.AUTHORIZATION, credentials)
                        .put(body)
                        .build();
            } else if (method.equalsIgnoreCase(Constants.METHOD_DELETE)) {
                request = new Request.Builder()
                        .url(url)
                        .addHeader(Constants.AUTHORIZATION, credentials)
                        .build();
            } else {
                throw new InvalidRequestException(method, Messages.INVALID_REQUEST);
            }

            Response response = client.newCall(request).execute();

            String networkResp = response.body().string();

            return new JSONObject(networkResp);
    }

	public JSONObject execute(String method, String url, JSONObject data, Map<String, String> header) throws IOException, InvalidRequestException, JSONException {

		MediaType JSON = MediaType.parse(Constants.APPLICATION_JSON_CHARSET_UTF_8);
		OkHttpClient client = getClient();

		Headers headers = createHeader(header);

		Request request;
		if (method.equalsIgnoreCase(Constants.METHOD_GET)) {
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.get()
					.build();
		} else if (method.equalsIgnoreCase(Constants.METHOD_POST)) {
			RequestBody body = RequestBody.create(JSON, data.toString());
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.post(body)
					.build();
		} else if (method.equalsIgnoreCase(Constants.METHOD_PUT)) {
			RequestBody body = RequestBody.create(JSON, data.toString());
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.put(body)
					.build();
		} else if (method.equalsIgnoreCase(Constants.METHOD_DELETE)) {
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.build();
		} else {
			throw new InvalidRequestException(method, Messages.INVALID_REQUEST);
		}

		Response response = client.newCall(request).execute();

		String networkResp = response.body().string();

		return new JSONObject(networkResp);
	}

	public JSONObject execute(String method, String url, String data, Map<String, String> header) throws IOException, InvalidRequestException, JSONException {

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10,TimeUnit.SECONDS)
				.readTimeout(30,TimeUnit.SECONDS)
				.build();

		Headers headers = createHeader(header);

		Request request;
		if (method.equalsIgnoreCase(Constants.METHOD_GET)) {
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.get()
					.build();
		} else if (method.equalsIgnoreCase(Constants.METHOD_POST)) {
			RequestBody body = RequestBody.create(JSON, data);
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.post(body)
					.build();
		} else if (method.equalsIgnoreCase(Constants.METHOD_PUT)) {
			RequestBody body = RequestBody.create(JSON, data);
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.put(body)
					.build();
		} else if (method.equalsIgnoreCase(Constants.METHOD_DELETE)) {
			request = new Request.Builder()
					.url(url)
					.headers(headers)
					.build();
		} else {
			throw new InvalidRequestException(method, Messages.INVALID_REQUEST);
		}

		Response response = client.newCall(request).execute();

		String networkResp = response.body().string();

		return new JSONObject(networkResp);
	}

	private OkHttpClient getClient() {
		return new OkHttpClient().newBuilder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.build();
	}

	private Headers createHeader(Map<String, String> header) {
		return Headers.of(header);
	}
}

