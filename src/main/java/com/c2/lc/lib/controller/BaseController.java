package com.c2.lc.lib.controller;

import com.c2.lc.lib.api.ApiResponse;
import com.c2.lc.lib.api.ResponseVO;
import com.c2.lc.lib.base.BaseSuper;
import com.c2.lc.lib.exceptions.*;
import com.c2.lc.lib.kafka.KafkaHelper;
import com.c2.lc.lib.properties.AppMessages;
import com.c2.lc.lib.properties.Messages;
import com.c2.lc.lib.security.AesCbcEncryption;
import com.c2.lc.lib.topics.MsApiLogTopic;
import com.c2.lc.lib.utils.AppStatus;
import com.c2.lc.lib.utils.Constants;
import com.c2.lc.lib.utils.OffsetRange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BaseController extends BaseSuper {

	private long requestCounter = 0L;
	private long responseCounter = 0L;
	private long exceptionCounter = 0L;

	@Autowired protected ObjectMapper objectMapper;

	@Autowired protected Validator validator;
	@Autowired protected KafkaHelper kafkaHelper;
	@Autowired protected AesCbcEncryption aesCbcEncryption;

	@Value("${fetch.max.size.limit:10000}")
	protected int maxSize;

	@Value("${api.connection.timeout:120000}")
	protected long apiConnectionTimeout;

	@Value("${infosec.api.response.setMessagesAndApiCall:true}")
	protected boolean enableAppMessagesAndApiCall;

	final static private String EXCEPTION_TOPIC_NAME = "ms-exceptions";
	@Autowired
	@Qualifier("error-kafka-template")
	private KafkaTemplate<String, MsApiLogTopic> kafkaTemplate;

	@GetMapping(path = "/ping", produces = "application/json")
	public ResponseEntity<ApiResponse> ping() {
		ApiResponse apiResponse = new ApiResponse(Constants.EMPTY_STRING, helper.getRandomUUID());
		try {
			JsonObject data = new JsonObject();
			data.addProperty("time", helper.getCurrentTimeString());
			data.addProperty("requests", requestCounter);
			data.addProperty("responses", responseCounter);
			data.addProperty("exceptions", exceptionCounter);

			setJsonPayload(apiResponse, data);
		} catch (Exception e) {
			this.handleAppExceptions(e, apiResponse);
		}
		return getResponseEntity(apiResponse);
	}

	protected void setDataJsonObjectPayload(ApiResponse response, JsonObject data) throws JsonProcessingException {
		JsonObject ret = new JsonObject();
		ret.add("data", data);
		this.setJsonPayload(response, ret);
	}

	protected void setDataJsonArrayPayload(ApiResponse response, JsonArray data) throws JsonProcessingException {
		JsonObject ret = new JsonObject();
		ret.add("data", data);
		this.setJsonPayload(response, ret);
	}

	protected JsonObject getDataJsonObject(String payload) throws InputPayloadException {
		JsonObject obj = helper.getJsonObject(payload);
		if (!obj.has("data"))  { throw new InputPayloadException("'data' element is missing!");}
		return obj.get("data").getAsJsonObject();
	}

	protected JsonArray getDataJsonArray(String payload) throws InputPayloadException {
		JsonObject obj = helper.getJsonObject(payload);
		if (!obj.has("data"))  { throw new InputPayloadException("'data' element is missing!");}
		return obj.get("data").getAsJsonArray();
	}

	protected void setJsonPayload(ApiResponse response, JsonElement data) throws JsonProcessingException {
		JsonObject resData = data.getAsJsonObject();
		if(resData.has("status") && !resData.get("status").getAsString().equalsIgnoreCase("success")){
			response.setAppStatusCode(6);
		}
		response.setPayloadJson(objectMapper.readValue(helper.toJson(data), Object.class));
	}

	protected ApiResponse initializeResponseWithoutLog(String message) {
		++requestCounter;
		final ApiResponse apiResponse = new ApiResponse(message);
		apiResponse.setTime(helper.getCurrentTime());
		return apiResponse;
	}

	protected ApiResponse initializeResponse(String message) {
		++requestCounter;
		log.info("API End point {}", message);
		final ApiResponse apiResponse = new ApiResponse(message, helper.getRandomUUID());
		apiResponse.setTime(helper.getCurrentTime());
		return apiResponse;
	}

/*
	protected ApiResponse initializeResponse(HttpEntity<?> httpEntity, String message) {
		log.debug(message);
		if (log.isDebugEnabled()) {
			log.debug(systemHelper.toJSON(httpEntity));
		}
		return new ApiResponse(message);
	}
*/
	private String getCallType(String message) {
		int endIndex = message.indexOf('/', 0);
		message = message.substring(0 , endIndex);
		return message.trim();
	}

	private String getApiUrl(String message) {
		int beginIndex = message.indexOf('/', 0);
		message = message.substring(beginIndex);
		return message.trim();
	}
/*
	protected void logAPIEntry(Logger logger, HttpEntity<?> httpEntity, String message) {
		log.debug(message);
		if (log.isDebugEnabled()) {
			log.debug(helper.toJSON(httpEntity));
		}
	}

*/
	protected String getMessage(String message, String tag, String value) {
		return message.replace(tag, value);
	}
	protected void handleAppExceptions(Exception e, ApiResponse apiResponse) {
		handleAppExceptions(e, apiResponse, null);
	}
	protected void handleAppExceptions(Exception e, ApiResponse apiResponse, Map<String, String> headers) {
		++exceptionCounter;
		StringBuilder api = new StringBuilder("API:" + apiResponse.getApiCall() + Constants.NEXT_LINE);
		String headersStr = "Headers: " + (helper.isEmpty(headers) ? Constants.HYPHEN : headers.toString()) + Constants.NEXT_LINE;
		String exception;
		String messageChain = Constants.EMPTY_STRING;

		if (e instanceof RecordNotFoundException) {
			exception = "RecordNotFoundException : " + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_RECORD_NOT_FOUND);
		} else if (e instanceof DuplicateRecordException) {
			exception = "DuplicateRecordException: " + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_DUPLICATE_RECORD);
		} else if (e instanceof SimpleException) {
			exception = "SimpleException: " + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_DATA_INPUT_ERROR);
		} else if (e instanceof UnAuthorizedException) {
			exception = "UnAuthorizedException: " + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_INVALID_REQUEST);
		} else if (e instanceof SessionExpiredException) {
			exception = "Session expired: " + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_SESSION_EXPIRED);
		} else if (e instanceof InputPayloadException) {
			exception = "InputPayloadException: " + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_DATA_INPUT_ERROR);
		} else if (e instanceof UserAuthenticationException) {
			exception = "UserAuthenticationException:"  + e.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_AUTHENTICATION_ERROR);
		} else if (e instanceof InvalidRequestException) {
			InvalidRequestException u = (InvalidRequestException) e;
			exception = "InvalidRequestException :" + u.getMessage();
			log.debug(api + headersStr + exception);
			apiResponse.setAppStatusCode(AppStatus.APP_CODE_INVALID_REQUEST);
		} else {
			if (e instanceof AppErrorException) {
				exception = "AppErrorException: " + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_APPLICATION_ERROR);
			} else if (e instanceof DataFormatException) {
				exception = "DataFormatException: " + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_DATA_INPUT_ERROR);
			}  else if (e instanceof InvalidDateException) {
				exception = "InvalidDateException: " + getAppMessage(Messages.INVALID_DATE);
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_DATA_INPUT_ERROR);
			} else if (e instanceof DataIntegrityViolationException) {
				exception = "DataIntegrityViolationException: " + getAppMessage(Messages.APPLICATION_ERROR);
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_DATA_INPUT_ERROR);
			} else if (e instanceof CommunicationErrorException) {
				exception = "CommunicationErrorException: " + e.getMessage();
				log.debug(api + headersStr + exception);
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_COMMUNICATION_ERROR);
			} else if (e instanceof ConnectTimeoutException) {
				exception = "ConnectTimeoutException: " + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_COMMUNICATION_ERROR);
			}  else if (e instanceof ConnectException) {
				exception = "ConnectException: " + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_COMMUNICATION_ERROR);
			}  else if (e instanceof SQLException) {
				exception  = "SQLException: Error Code -> " + ((SQLException) e).getSQLState() + Constants.NEXT_LINE +
						"SQL State -> " + ((SQLException) e).getSQLState() + Constants.NEXT_LINE +
						"Message - >" + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_SQL_EXCEPTION);
			} else if (e instanceof JDBCConnectionException) {
				exception  = "JDBCConnectionException: Error Code -> " + ((JDBCConnectionException) e).getSQLState() + Constants.NEXT_LINE +
						"SQL State -> " + ((JDBCConnectionException) e).getSQLState() + Constants.NEXT_LINE +
						"Message - >" + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_SQL_EXCEPTION);
			} else if (e instanceof NullPointerException) {
				exception = "NullPointerException: " + e.getMessage();
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_APPLICATION_ERROR);
			} else if (e instanceof ErrorException) {
				exception = "Exception: " + e.getMessage();
				apiResponse.setAppStatusCode(((ErrorException) e).getErrorCode());
			} else {
				messageChain = getExceptionMessageChain(e.getCause());
				exception = String.format("%s -> %s", getAppMessage(Messages.APPLICATION_ERROR), messageChain);
				apiResponse.setAppStatusCode(AppStatus.APP_CODE_APPLICATION_ERROR);
				if (!enableAppMessagesAndApiCall) {
					exception = Constants.EMPTY_STRING;
				} else {
					exception += String.format ("Exception : %s", messageChain);
				}
			}

			String exceptionAsString =  "Stack Trace: " + getStackTrace(e) + Constants.NEXT_LINE;
			log.error(api + headersStr + exception + Constants.NEXT_LINE + exceptionAsString, e);

			try {
				StringBuilder msg = new StringBuilder("Request Id: ").append(apiResponse.getRequestId()).append(Constants.NEXT_LINE)
						.append(api).append(Constants.NEXT_LINE)
						.append(headersStr).append(Constants.NEXT_LINE)
						.append(exception).append(Constants.NEXT_LINE)
						.append(exceptionAsString);
				kafkaTemplate.send(EXCEPTION_TOPIC_NAME, MsApiLogTopic.builder().payload(msg.toString()).dateTime(helper.getCurrentTime()).build());
				log.info("Pushed alert message: {}", apiResponse.getRequestId());
			} catch (Exception e1) {
				log.error(exception, e1);
			}
		}

		// add exception to message
		apiResponse.getMessages().add(exception);
	}

	private String getStackTrace (Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	private String getExceptionMessageChain(Throwable throwable) {
		List<String> result = new ArrayList<>();
		while (throwable != null) {
			result.add(throwable.getMessage());
			throwable = throwable.getCause();
		}
		return StringUtils.join(result, ", ");
	}

	protected ResponseEntity<ApiResponse> getResponseEntity(ApiResponse response) {
		return getResponseEntity(response, response.getMessages(), HttpStatus.OK, null);
	}

	protected ResponseEntity<ApiResponse> getResponseEntity(ApiResponse response, HttpStatus status) {
		return getResponseEntity(response, response.getMessages(), status, null);
	}

	protected ResponseEntity<ApiResponse> getResponseEntity(ApiResponse response, List<String> messages) {
		return getResponseEntity(response, messages, HttpStatus.OK, null);
	}

	protected ResponseEntity<ApiResponse> getResponseEntity(ApiResponse response, List<String> messages, HttpStatus status) {
		return getResponseEntity(response, messages, status, null);
	}

	protected ResponseEntity<ApiResponse> getResponseEntity(ApiResponse response, HttpStatus status, HttpHeaders headers) {
		return getResponseEntity(response, response.getMessages(), status, headers);
	}

	protected ResponseEntity<ApiResponse> getResponseEntity(ApiResponse response, List<String> messages, HttpStatus status, HttpHeaders headers) {
		++ responseCounter;
		response.setMessages(messages);
		//suppress info for prod
		if (!enableAppMessagesAndApiCall) {
			response.setApiCall(Constants.EMPTY_STRING);
			response.setPayloadClass(null);
		}
		response.setSeconds(helper.diffInSeconds(response.getTime(), helper.getCurrentTime()));
		log.info("RequestId {} completed with status {} in {} Secs", response.getRequestId(), response.getAppStatusCode(), response.getSeconds());
		return new ResponseEntity(response, headers, status);
	}

	protected ResponseEntity<?> getBareResponseEntity() {
		return new ResponseEntity(HttpStatus.OK);
	}
	protected ResponseEntity<?> getBareResponseEntity(ApiResponse response) {
		//suppress info for prod
		if (!enableAppMessagesAndApiCall) {
			response.setApiCall(Constants.EMPTY_STRING);
			response.setPayloadClass(null);
		}
		return new ResponseEntity(response, HttpStatus.OK);
	}

	protected void validateInputPayload(Object vo) throws InputPayloadException {
		ArrayList<String> messages = new ArrayList<>();

		if (vo == null) {
			messages.add(getAppMessage(Messages.INPUT_PAYLOAD_CANNOT_BE_NULL));
			throw new InputPayloadException(messages);
		}

		DirectFieldBindingResult result = new DirectFieldBindingResult(vo, vo.getClass().getName());
		validator.validate(vo, result);
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			Iterator<ObjectError> iterator = errors.iterator();
			ObjectError obj;
			while (iterator.hasNext()) {
				obj = iterator.next();
				messages.add(obj.getDefaultMessage());
			}
			throw new InputPayloadException(messages);
		}
	}

	protected void addMessage(ApiResponse apiResponse, String msg) {
//		apiResponse.getMessages().add(getAppMessage(msg, helper.getLocale(apiResponse.getHeaders())));
		apiResponse.getMessages().add(msg);
	}
	protected String getAppMessage(String msg) {
		return AppMessages.getPropertyValue(msg);
	}

	protected void addMessage(ApiResponse apiResponse, String msg, String find, String replace) {
		apiResponse.getMessages().add(getAppMessage(msg, find, replace));
	}
	protected String getAppMessage(String msg, String find, String replace) {
		return getAppMessage(msg).replace(find, replace);
	}

	protected void addMessage(ApiResponse apiResponse, String msg, String locale) {
		apiResponse.getMessages().add(getAppMessage(msg, locale));
	}
	protected String getAppMessage(String msg, String locale) {
		return AppMessages.getPropertyValue(msg, locale);
	}

	protected void addMessage(ApiResponse apiResponse, String msg, String locale, String find, String replace) {
		apiResponse.getMessages().add(getAppMessage(msg, locale, find, replace));
	}
	protected String getAppMessage(String msg, String locale, String find, String replace) {
		return getAppMessage(msg, locale).replace(find, replace);
	}


	private static final String USER_ID = "x-csquare-user-id";
	protected Long getUserId(Map<String, String> headers) throws InvalidRequestException {
		String id = headers.get(USER_ID);
		if (helper.isEmpty(id)) {
			throw new InvalidRequestException("", "User id is not set!");
		}
		return Long.parseLong(id);
	}

	protected String getClassification(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_CLASSIFICATION));
	}

	protected String getAppId(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_APP_ID));
	}

	protected String getIpAddress(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_IP_ADDRESS));
	}

	protected String getDeviceId(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_DEVICE_ID));
	}

	protected String getAuthToken(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_AUTH_TOKEN));
	}

	protected String getLoginId(HttpHeaders header)  {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_LOGIN_ID));
	}

	protected String getPartnerId(HttpHeaders header)  {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_PARTNER_ID));
	}

	protected String getLatitude(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_LATITUDE));
	}

	protected String getLongitude(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_LONGITUDE));
	}

	protected void logFormattedPayload(ApiResponse vo, ResponseEntity<ResponseVO> responseEntity) {
		if (log.isDebugEnabled()) {
			if (isGsonON() && vo.getPayloadClass() != null) {
				Object obj = vo.getPayloadJson();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				log.debug(gson.toJson(obj));
			} else {
				log.debug(helper.toJSON(responseEntity));
			}
		}
	}

	private boolean isGsonON() {
		return !helper.getSystemProperty("gson", "true");
	}

	protected OffsetRange validatePageRequest(Integer page, Integer size) throws AppErrorException {
		if (page < 0) { throw new AppErrorException(page, Messages.INVALID_PAGE); }
		if (size > Constants.MAX_RESULTS_LIST_COUNT) { throw new AppErrorException(size, Messages.TOO_MANY_RECORDS); }
		return new OffsetRange(page, size);
    }

    protected Object getValidatedBody(HttpEntity<?> httpEntity) throws InputPayloadException {
	    final Object body = httpEntity.getBody();
	    validateInputPayload(body);
	    return body;
	}



}
