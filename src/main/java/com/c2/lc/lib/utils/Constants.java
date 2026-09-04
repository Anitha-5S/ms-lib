package com.c2.lc.lib.utils;

import org.springframework.http.MediaType;

import java.math.BigDecimal;

public class Constants {

	private Constants() { super (); }

	public static final String AUTHORIZATION = "Authorization";
	public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
	public static final String APPLICATION_WWW_FORM_URLENCODED = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
	public static final String APPLICATION_MULTIPART_FORM_DATA = MediaType.MULTIPART_FORM_DATA_VALUE;
	public static final String ENVIRONMENT_PRODUCTION = "prod";
	public static final String ENVIRONMENT_STAGE = "stage";

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";


	public static final int MOBILE_USER = 2;
	public static final int TABLET_USER = 3;
	public static final int WEB_USER = 4;
	public static final int RESET_LOGIN_ATTEMPTS = 0;
	public static final int INT_VALUE_ZERO = 0;
	public static final long LONG_VALUE_ZERO = 0L;
	public static final int INT_VALUE_ONE = 1;
	public static final int MAX_RESULTS_LIST_COUNT = 100;
	public static final int PAGE_SIZE = 25;

	public static final int HTTP_CODE_OK = 200;
	public static final int HTTP_CODE_BAD_REQUEST = 400;
	public static final int HTTP_CODE_INTERNAL_SERVER_ERROR = 500;

	public static final long INVALID_ID = -1L ;
	public static final long ROOT_USER = 1L;

	public static final double DOUBLE_VALUE_ZERO = 0.0;
	public static final double DOUBLE_VALUE_ONE = 1.0;
	public static final double DOUBLE_VALUE_HUNDRED = 100.0;
	public static final String DOUBLE_STRING_VALUE_ZERO = "0.0";

	public static final BigDecimal BD_VALUE_ZERO = new BigDecimal("0.0");
	public static final BigDecimal BD_VALUE_ONE = new BigDecimal("1.0");

	public static final String STRING_VALUE_ZERO = "0";
	public static final String STRING_VALUE_ONE = "1";
	public static final String STRING_VALUE_TWO = "2";
	public static final String STRING_VALUE_THREE = "3";

	public static final String ENCODING_FORMAT = "UTF-8";
	public static final String HYPHEN = "-";
	public static final String EMPTY_STRING = "";

	public static final String E_INVOICE = "I_GENERATE_IRN_E_INVOICE";
	public static final String E_INVOICE_CANCEL = "I_CANCEL_IRN_E_INVOICE";

	public static final String LOCALE_DEFAULT = "en_US";
	public static final String LANGUAGE_ENGLISH = "en";
	public static final String SHA_ALGORITHM = "HmacSHA256";
	public static final String LANGUAGE_KANNADA = "ka";
	public static final String LANGUAGE_ENGLISH_MESSAGES_PROPERTIES_FILENAME = "en-messages.properties";
	public static final String LANGUAGE_KANNADA_MESSAGES_PROPERTIES_FILENAME = "ka-messages.properties";
	public static final String LANGUAGE_ENGLISH_NOTIFICATIONS_PROPERTIES_FILENAME = "en-notifications.properties";
	public static final String LANGUAGE_KANNADA_NOTIFICATIONS_PROPERTIES_FILENAME = "ka-notifications.properties";

	public static final String HEADER_USER_ID = "X-csquare-user-id";
	public static final String HEADER_C2CODE = "X-csquare-c2code";
	public static final String HEADER_BRCODE = "X-csquare-brcode";
	public static final String HEADER_API_KEY_ID = "X-csquare-api-key";
	public static final String HEADER_API_TOKEN = "X-csquare-api-token";
	public static final String HEADER_LOCALE = "X-csquare-locale";																	

	public static final String HEADER_LOGIN_ID = "X-com-invictus-login-id";
	public static final String HEADER_PARTNER_ID = "X-com-invictus-partner-id";
	public static final String HEADER_AUTH_TOKEN = "X-com-invictus-auth-token";
	public static final String HEADER_DEVICE_ID = "X-com-invictus-device-id";
	public static final String HEADER_IP_ADDRESS = "X-com-invictus-ip-address";
	public static final String HEADER_LATITUDE = "X-com-invictus-latitude";
	public static final String HEADER_LONGITUDE = "X-com-invictus-longitude";
	public static final String HEADER_CLASSIFICATION= "X-com-invictus-classification";
	public static final String HEADER_APP_ID = "X-com-invictus-app-id";

	public static final String HEADER_ACCESS_CONTROL = "Access-Control-Allow-Origin";
	public static final String HEADER_ACCESS_HEADER = "Access-Control-Allow-Headers";
	public static final String HEADER_HOST = "host";
	public static final String HEADER_ACCEPT = "accept";
	public static final String HEADER_ACCEPT_ENCODING = "accept-encoding";
	public static final String HEADER_ACCEPT_LANGUAGE = "accept-language";
	public static final String HEADER_CONTENT_TYPE = "content-type";
	public static final String HEADER_ORIGIN = "origin";
	public static final String HEADER_REFERER = "referer";
	public static final String HEADER_USER_AGENT = "user-agent";
	public static final String HEADER_CONNECTION = "connection";
	public static final String HEADER_FORWARD_PORT = "forward_port";
	public static final String HEADER_FORWARD_PROTO = "forward_proto";
	public static final String HEADER_FORWARD_HOST = "forward-host";
	public static final String HEADER_FORWARD_SERVER = "forward-server";

	public static final String UNIMPLEMENTED_METHOD_INVOCATION = "Unimplemented method invocation!";

	public static final String TIMESTAMP_NULL = "0000-00-00 00:00:00";
	public static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SQL_TIME_FORMAT = "HH:mm:ss";
	public static final String SQL_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SQL_TIMESTAMP_MILLI_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_DDMMYY = "dd-MM-yy";
	public static final String DATE_FORMAT_DDMMYYYY = "dd-MM-yyyy";
	public static final String DATE_FORMAT_DDMMYYYYHHMMSS = "dd-MM-yyyy HH:mm:ss";
	public static final String EXPIRY_DATE_FORMAT_MMYYYY = "MM-YYYY";
	public static final String DEFAULT_MMDD_DOB = "-01-01";
	public static final String DEFAULT_ACCEPTED_TIMESTAMP = "2000-01-01 12:19:29";
	public static final String BEGIN_DAY_TIME = " 00:00:00";
	public static final String END_DAY_TIME = " 23:59:59";
	public static final String POST_TIME_FORMAT="yyyyMMddHHmmSS";
	public static final String DD_MM_YYYY = "dd/MM/yyyy";

	public static final String EMAIL_FORMAT = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
	public static final String ALPHABETS_ONLY_FORMAT = "^(([A-Za-z]+\\s{1}[A-Za-z]+)|([A-Za-z]+))$";
	public static final String NAMES_FORMAT = "[a-zA-Z0-9. ]*";
	public static final String NUMERIC_ONLY_FORMAT = "^[0-9]*$";
	public static final String ALPHA_NUMERIC_FORMAT = "[a-zA-Z0-9/]*";
	public static final String DOUBLE_FORMAT = "(-?[0-9]*\\.?[0-9]+)|(-?[0-9]+\\.?[0-9]*)$";
	public static final String LOGIN_NAME_FORMAT = "[a-zA-Z0-9._@]*";
	public static final String NOT_APPLICABLE_DATA = "#NA#";
	public static final String SERIAL_NUMBER_ZEROS = "00000000";
	public static final String NAME_EXPRESSION = "^[a-zA-Z .]+$";
	public static final String EMAIL_OR_MOBILE = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*|[0-9]{10,16}$";
	
	public static final String FORWARD_SLASH = "/";
	public static final String PIPE = "|";
	public static final String SINGLE_SPACE = " ";
	public static final String SPECIAL_CHARACTERS = "!@#$%^&*";
	public static final String COMMA = ",";
	public static final String NEXT_LINE = "\n";
	public static final String NEXT_LINE_2 = "\n\n";
	public static final String HTML_LINEBREAK = "<br/>";
	public static final String URL_SPACE = "%20";
	public static final String PERCENTAGE = "%";
	public static final String ESCAPE_PERCENTAGE ="\\%";
	public static final String SPACE_DELIMITER = " ";
	public static final String HTML_LINE = "<br>";


	public static final int HASH_TOKEN_LENGTH = 125;

	public static final String ROLE_SELLER = "S";
	public static final String ROLE_BUYER = "B";
	public static final String ROLE_SALES_MAN = "SALES_MAN";
	public static final String ROLE_DELIVERY_MAN = "DELIVERY_MAN";

	// status
	public static final String STATUS_PAID = "P";
	public static final String STATUS_VOID = "V";
	public static final String STATUS_YES = "Y";
	public static final String STATUS_NO = "N";
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "N";
	public static final String STATUS_PENDING = "P";

	public static final String ROOT_DATA = "data";

	public static final String APP_PING_URI = "/ping";
	public static final String BRANCH_ID = "n_branch_id";
	public static final String CUSTOMER_ID = "n_customer_id";
	public static final String USER_ID = "n_user_id";

	public static final String GENERATE_IRN = "%seinvoice/invoice/v1.0";
	public static final String CANCEL_IRN = "%seinvoice/cancel/v1.0";
	public static final String GENERATE_EWB_IRN = "%seinvoice/generateEwaybill/1.0";
	public static final String GET_IRN_INVOICE = "einvoice/invoice/irn/v1.0?IRN=";
	public static final String INVOICE_GST_SYNC = "einvoice/SyncGstinDetails/1.0?gstin=";
	public static final String EWB_BILL_IRN = "/einvoice/getEwaybill/1.0";
	/*public static final String USER_ID = "n_user_id";
	public static final String USER_ID = "n_user_id";*/


}
