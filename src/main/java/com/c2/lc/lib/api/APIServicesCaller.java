package com.c2.lc.lib.api;

import com.c2.lc.lib.utils.Constants;
import com.c2.lc.lib.utils.SystemHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Type;


public class APIServicesCaller {

	private static final Logger logger = Logger.getLogger(APIServicesCaller.class);
	private static APIServicesCaller apiServicesCaller = null;

	private SystemHelper helper = new SystemHelper ();
	private Client client = null;

	private static final String GET_REQUEST = "\n============GET Request============";
	private static final String PUT_REQUEST= "\n============PUT Request============";
	private static final String POST_REQUEST = "\n============POST Request============";
	private static final String DELETE_REQUEST = "\n============DELETE Request============";


	public static APIServicesCaller getInstance() {
		if (apiServicesCaller == null) {
			apiServicesCaller = new APIServicesCaller();
		}
		return apiServicesCaller;
	}

	public ResultVO get(Class<?> payload, String url, MsHeaderParams header, int offset, int length) {
		ResultVO resultVO=null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url + "/"+offset + "/"+length;
			logger.debug(GET_REQUEST);
			logger.debug(u);
			logInputPayload(payload);

			ClientResponse response = getBuilder(header, u).get(ClientResponse.class);

			resultVO = getResponseObject(payload, response);
			
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO get(Class<?> payload, String url, MsHeaderParams header, String parameters) {
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url + "/"+parameters;
			logger.debug(GET_REQUEST);
			logger.debug(u);
			logInputPayload(payload);

			ClientResponse response = getBuilder(header, u).get(ClientResponse.class);

			resultVO = getResponseObject(payload, response);
			
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO get(Class<?> payload, String url, MsHeaderParams header, long id) {
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url + "/"+id;
			logger.debug(GET_REQUEST);
			logger.debug(u);
			logInputPayload(payload);

			ClientResponse response = getBuilder(header, u).get(ClientResponse.class);

			resultVO = getResponseObject(payload, response);
			
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO get(Class<?> payload, String url, MsHeaderParams header) {
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url;
			logger.debug(GET_REQUEST);
			logger.debug(u);

			ClientResponse response = getBuilder(header, u).get(ClientResponse.class);

			resultVO = getResponseObject(payload, response);

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO post(String url, MsHeaderParams header, Object payload, Class<?> type) {
		ResultVO resultVO = null ;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url;
			String json = helper.toJSON(payload);
			logger.debug(POST_REQUEST);
			logger.debug(u);
			logInputPayload(payload);

			ClientResponse response = getBuilder(header, u).post(ClientResponse.class, json);

			resultVO = getResponseObject(type, response);

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO put(String url, MsHeaderParams header, Object payload, Class<?> type) {
		ResultVO resultVO = null;
		try{
			initializeClient();
			
			String u = header.getBaseURL() + url;
			String json = helper.toJSON(payload);
			logger.debug(PUT_REQUEST);
			logger.debug(u);
			logInputPayload(payload);
			
			ClientResponse response = getBuilder(header, u).put(ClientResponse.class, json);

			resultVO = getResponseObject(type, response);

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	private void logInputPayload(Object payload){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.debug(gson.toJson(payload));
	}
	public ResultVO delete(String url, MsHeaderParams header) {
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url;
			logger.debug(DELETE_REQUEST);
			logger.debug(u);

			ClientResponse response = getBuilder(header, u).delete(ClientResponse.class);

			resultVO = getResponseObject(null, response);

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO delete(String url, MsHeaderParams header, String parameter) {
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url + "/"+parameter;
			logger.debug(DELETE_REQUEST);
			logger.debug(u);

			ClientResponse response = getBuilder(header, u).delete(ClientResponse.class);

			resultVO = getResponseObject(null, response);

			response.close();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO delete(String url, MsHeaderParams header, @SuppressWarnings("rawtypes") Class type) {
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url;
			logger.debug(DELETE_REQUEST);
			logger.debug(u);

			ClientResponse response = getBuilder(header, u).delete(ClientResponse.class);

			resultVO = getResponseObject(type, response);

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}

	public ResultVO getList(Type listType, String url, MsHeaderParams header) {
		ResponseVO output = null ;
		ResultVO resultVO = null;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url;
			logger.debug("\n============SEARCH Request============");
			logger.debug(u);

			ClientResponse response = getBuilder(header, u).get(ClientResponse.class);

			output = (ResponseVO) helper.fromJSON(response.getEntity(String.class), ResponseVO.class);
			response.getHeaders();
			logger.debug("\n============SEARCH Response============");
			logger.debug(helper.toJSON(output));

			resultVO = getListResultVO(listType, output);
			resultVO.setAuthToken(this.getAuthTokenFromResponse(response));

			response.close();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}
	
	public ResultVO postXML(String url, MsHeaderParams header, String xml, Class<?> type) {
		ResultVO resultVO = null ;
		try {
			initializeClient();
			
			String u = header.getBaseURL() + url;
			logger.debug(POST_REQUEST);
			logger.debug(u);
			logger.debug(xml);
			
			ClientResponse response = getBuilderForXML(header, u).post(ClientResponse.class, xml);

			resultVO = getResponseObject(type, response);

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return resultVO;
	}


	///////////////////////////////// helpers ////////////////////////////////////////////
	
	private void initializeClient() {
		if (client == null) {
			client = Client.create();
			/*client.addFilter(new GZIPContentEncodingFilter(true));*/
		}
	}

	private Builder getBuilder(MsHeaderParams header, String u) {
		WebResource webResource = client.resource(u);
		return webResource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.header(Constants.HEADER_LOGIN_ID, header.getLoginId())
				.header(Constants.HEADER_PARTNER_ID, header.getPartnerId())
				.header(Constants.HEADER_AUTH_TOKEN, header.getAuthToken())
				.header(Constants.HEADER_DEVICE_ID, header.getDeviceId())
				.header(Constants.HEADER_IP_ADDRESS, header.getIpAddress())
				.header(Constants.HEADER_LATITUDE, header.getLatitude())
				.header(Constants.HEADER_LONGITUDE, header.getLongitude())
				.header(Constants.HEADER_APP_ID, header.getAppId())
				.header(Constants.HEADER_CLASSIFICATION, header.getClassification());
	}

	private Builder getBuilderForXML(MsHeaderParams header, String u) {
		WebResource webResource = client.resource(u);
		return webResource.accept(MediaType.APPLICATION_XML)
				.type(MediaType.APPLICATION_XML)
				.header(Constants.HEADER_DEVICE_ID, header.getDeviceId())
				.header(Constants.HEADER_IP_ADDRESS, header.getIpAddress())
				.header(Constants.HEADER_LATITUDE, header.getLatitude())
				.header(Constants.HEADER_LONGITUDE, header.getLongitude());
	}

	private ResultVO getResponseObject(Class<?> type, ClientResponse response) {
		ResponseVO output;
		ResultVO resultVO;
		String output2 = response.getEntity(String.class);
		logger.debug("\n============ Response============");
		output = (ResponseVO) helper.fromJSON(output2, ResponseVO.class);

		resultVO = getResultVO(type, output);
		resultVO.setAuthToken(this.getAuthTokenFromResponse(response));
		
		response.close();
		
		return resultVO;
	}

	private ResultVO getResultVO(Class<?> payload, ResponseVO output) {
		ResultVO result = getResultVO(output);

		if (payload != null && output.getPayload() != null) {
			String string = output.getPayload();
			result.setPayload(helper.fromJSON(string, payload));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			logger.debug(gson.toJson(result.getPayload()));
		}
		return result ;
	}

	private ResultVO getResultVO(ResponseVO output) {
		ResultVO result = new ResultVO();
		result.setAppStatusCode(output.getAppStatusCode());
		result.setUnread(output.getUnread());
		result.setMessages(output.getMessages());
		return result;
	}

	private String getAuthTokenFromResponse(ClientResponse response) {
		MultivaluedMap<String, String> header = response.getHeaders();
		return header.getFirst(Constants.HEADER_AUTH_TOKEN);
	}

	private ResultVO getListResultVO(Type listType, ResponseVO output) {
		ResultVO result = getResultVO(output);

		if (output.getPayload() != null) {
			String string = output.getPayload();
			result.setPayload(helper.fromJSON(string, listType));
		}
		return result ;
	}
	
}
