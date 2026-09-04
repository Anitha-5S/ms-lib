package com.c2.lc.lib.utils;


import com.azure.messaging.eventhubs.EventData;
import com.c2.lc.lib.eventhub.EventHubUtil;
import com.c2.lc.lib.exceptions.DataFormatException;
import com.c2.lc.lib.exceptions.InputPayloadException;
import com.c2.lc.lib.security.RandomPasswordGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SystemHelper {

	private String ZONE_ID = "Asia/Kolkata";
	private String HTTPS = "https://";

	public String toXML(String str) {
		JSONObject object = new JSONObject(str);
		return XML.toString(object);
	}

	public double roundThreeDecimals(double d) {
		DecimalFormat threeForm = new DecimalFormat("#.###");
		return Double.valueOf(threeForm.format(d));
	}

	public JsonObject getJsonObject (String name, String value) {
		JsonObject data = new JsonObject();
		data.addProperty(name, value);
		return data;
	}

	public JsonObject getJsonObject (String name, JsonElement value) {
		JsonObject data = new JsonObject();
		data.add(name, value);
		return data;
	}


	public String toString(Object obj) { return obj == null ? null : obj.toString(); }

	public String getEncodedString(String param) {
		return new String(Base64.encodeBase64(param.getBytes()));
	}

	public String getDecodedString(String param) {
		return new String(Base64.decodeBase64(param.getBytes()));
	}

	public String getURLEncodedString(String param) throws UnsupportedEncodingException {
		return URLEncoder.encode(param, Constants.ENCODING_FORMAT);
	}

	public String getURLDecodedString(String param) throws UnsupportedEncodingException {
		return URLDecoder.decode(param, Constants.ENCODING_FORMAT);
	}

	public ZoneId getZoneId() {
		return ZoneId.of(ZONE_ID);
	}

	public Long getNullableId(String id) throws DataFormatException {
		Long lId = null;
		try {
			if (id != null) {lId = Long.valueOf(id);}
		} catch (Exception e) {

			throw new DataFormatException(id);
		}
		return lId;
	}

	public Long validateId(String id) throws DataFormatException {
		Long lId;
		try {
			lId = Long.valueOf(id);
		} catch (Exception e) {
			throw new DataFormatException(id);
		}

		return lId;
	}

	public double timeDiffInMinutes(LocalDateTime fromDate, LocalDateTime toDate) {
		return timeDiffInSeconds(fromDate, toDate) / 60.0;
	}

	public long timeDiffInSeconds(LocalDateTime fromDate, LocalDateTime toDate) {
		return ChronoUnit.SECONDS.between(fromDate, toDate);
	}

	public long timeDiffInDays(LocalDateTime fromDate, LocalDateTime toDate) {
		return ChronoUnit.DAYS.between(fromDate, toDate);
	}

	public long timeDiffInDays(LocalDate fromDate, LocalDate toDate) {
		return ChronoUnit.DAYS.between(fromDate, toDate);
	}


	public LocalDate getLocalDate(String date) {
		return LocalDate.parse(date, getDateFormatter());
	}

	public LocalDateTime getLocalDateTime(String dateTime) {
		return LocalDateTime.parse(dateTime, getDateTimeFormatter());
	}

	public LocalDate getCurrentDate() {
		return getCurrentTime().toLocalDate();
	}
	public LocalDateTime getCurrentTime() {
		ZonedDateTime zdt = ZonedDateTime.now( getZoneId() ) ;
		return zdt.toLocalDateTime();
	}
	public String getCurrentDateTime(String pattern) {
		return getCurrentTime().format(DateTimeFormatter.ofPattern(pattern));
	}

	public String getCurrentDateString() {
		return this.convertDateToString(getCurrentDate());
	}

	public String getCurrentTimeString() {
		return this.convertTimeToString(getCurrentTime());
	}

	public Long getTimeInSeconds(LocalDateTime time) {
		return time.atZone( getZoneId()).toEpochSecond();
	}

	public Long getTimeInSeconds(LocalDate date) {
		return date.atStartOfDay().atZone( getZoneId()).toEpochSecond();
	}

	public Long getCurrentTimeMilliSeconds() {
		return getCurrentTime().atZone(getZoneId()).toInstant().toEpochMilli();
	}

	public String convertDateToString(LocalDate dt) {
		return convertDateToString(dt, Constants.SQL_DATE_FORMAT);
	}

	public String convertDateToString(LocalDate dt, String format) {
		return (dt == null) ? null : dt.format(DateTimeFormatter.ofPattern(format));
	}

	public LocalDate convertStringToDate(String date) {
		return (isEmpty(date)) ? null :  LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.SQL_DATE_FORMAT));
	}

	public LocalDate convertStringToDate(String date, String format) {
		return (isEmpty(date)) ? null :  LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
	}

	public String convertTimeToString() {
		return convertTimeToString(getCurrentTime());
	}

	public String convertTimeToDDMMYYYYHHMMSS() {
		return getCurrentTime().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_DDMMYYYYHHMMSS));
	}

	public String convertTimeToString(LocalDateTime dt) {
		return (dt == null) ? null : getDateTimeFormatter().format(dt);
	}

	public LocalDateTime convertStringToTime(String dt) {
		return isEmpty(dt) ? null : LocalDateTime.parse(dt, getDateTimeFormatter());
	}

	public DateTimeFormatter getDateFormatter() {
		return DateTimeFormatter.ofPattern(Constants.SQL_DATE_FORMAT);
	}

	public DateTimeFormatter getDateTimeFormatter() {
		return new DateTimeFormatterBuilder()
				.appendOptional(DateTimeFormatter.ofPattern(Constants.SQL_TIMESTAMP_FORMAT))
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
				.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				.toFormatter();
	}

	public int localDateDiff(LocalDate toDate, LocalDate fromDate) {
		int date = 0;
		if (!isEmpty(fromDate) && !isEmpty(toDate)) {
			date = (int) Duration.between(toDate.atStartOfDay(), fromDate.atStartOfDay()).toDays();
		}
		return date;
	}

	public boolean isDateExpired(LocalDateTime logoutOrInvalidationDatetime) {
		boolean ret = false;
		if (logoutOrInvalidationDatetime != null && getCurrentTime().compareTo(logoutOrInvalidationDatetime) >= 0) {
			ret = true;
		}
		return ret;
	}

	public LocalDateTime parseToLocalDateTime(String dateTime, String pattern) {
		return  LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
	}

	public <T> T fromJson(String param, Type listType) {
		return getGSonBuilder().fromJson(param, listType);
	}

	public <T> T fromJson(String jsonObject, Class<T> classTypeT) {
		return getGson().fromJson(jsonObject, classTypeT);
	}

	public <T> T fromJson(JsonObject jsonObject, Class<T> classTypeT) {
		return getGson().fromJson(jsonObject, classTypeT);
	}

	//TODO replace the below with above
	public <T> T fromJSON(String param, Type listType) {
		return getGSonBuilder().fromJson(param, listType);
	}
	public <T> T fromJSON(String jsonObject, Class<T> classTypeT) {
		return getGson().fromJson(jsonObject, classTypeT);
	}
	public <T> T fromJSON(JsonObject jsonObject, Class<T> classTypeT) {
		return getGson().fromJson(jsonObject, classTypeT);
	}
	/////////////////

	public String toJSON(Object parm) {
		Gson gson = getGSonBuilder();
		return gson.toJson(parm);
	}

	private Gson getGSonBuilder() {
		return new GsonBuilder().setDateFormat(Constants.SQL_TIMESTAMP_FORMAT)
				.setLongSerializationPolicy(LongSerializationPolicy.STRING)
				.create();
	}

	public boolean getSystemProperty(String param, String value) {
		String auth = System.getProperty(param);
		return !(auth != null && auth.equalsIgnoreCase(value));
	}

	public boolean isExpired(LocalDateTime expiryTime) {

		if (expiryTime == null || expiryTime.toString().equals(Constants.TIMESTAMP_NULL))
			return false; // if expiry days is null, then no expiration verification

		return (expiryTime.isBefore(getCurrentTime()));
	}

	public String generateNonce() throws NoSuchAlgorithmException {
		String nonce;
		// Initialize SecureRandom
		// This is a lengthy operation, to be done only upon
		// initialization of the application
		SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

		// generate a random number
		String randomNum = Integer.toString(prng.nextInt());

		// get its digest
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] result = sha.digest(randomNum.getBytes());

		nonce = randomNum + hexEncode(result);

		return nonce;
	}

	private String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		for (byte b : aInput) {
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}

	public String getFinancialYear(LocalDate date) {

		int year = date.getYear();
		int month = date.getMonthValue();

		String strDateFormat = "yy";
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(strDateFormat);
		String y = sdf.format(date);
		Integer yr = Integer.parseInt(y);
		String financialYear;
		if (month < 4) {
			financialYear = (year - 1) + "-" + yr;
		} else {
			financialYear = year + "-" + (yr + 1);
		}
		return financialYear;
	}
	public String getCurrentFinancialYearYY() {
		LocalDate date = getCurrentDate();
		String year = "";
		if (date.getMonthValue() > 3) {
			year = String.valueOf(date.getYear()).substring(2);
		} else {
			year = String.valueOf(date.getYear() - 1).substring(2);
		}
		return year;
	}
	public int getCurrentFinancialYear() {
		LocalDate date = getCurrentDate();
		int year;
		if (date.getMonthValue() > 3) {
			year = date.getYear();
		} else {
			year = date.getYear() - 1;
		}
		return year;
	}

	public int getCurrentFinancialYearBasedOnLocalDate(LocalDate date) {
		int year;
		if (date.getMonthValue() > 3) {
			year = date.getYear();
		} else {
			year = date.getYear() - 1;
		}
		return year;
	}

	public boolean validateHMACHash(String hMacAlgorithm, String secretKey, String requestHash, String request)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String digest = generateHMacHash(hMacAlgorithm, secretKey, request);

		return digest.equals(requestHash);
	}

	public String generateHMacHash(String hMacAlgorithm, String secretKey, String request) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec key = new SecretKeySpec((secretKey).getBytes(StandardCharsets.UTF_8), hMacAlgorithm);
		Mac mac = Mac.getInstance(hMacAlgorithm);
		mac.init(key);
		byte[] hashInBytes = mac.doFinal(request.getBytes(StandardCharsets.UTF_8));
		return java.util.Base64.getUrlEncoder().encodeToString(hashInBytes);
	}

	public String generateHMacHashToHex(String hMacAlgorithm, String secretKey, String request) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec key = new SecretKeySpec((secretKey).getBytes(StandardCharsets.UTF_8), hMacAlgorithm);
		Mac mac = Mac.getInstance(hMacAlgorithm);
		mac.init(key);
		byte[] hashInBytes = mac.doFinal(request.getBytes(StandardCharsets.UTF_8));
		return String.format("%064x", new BigInteger(1, hashInBytes));
	}

	public String getGeneratedPassword() {
		int noOfCAPSAlpha = 1;
		int noOfDigits = 1;
		int noOfSplChars = 1;
		int minLen = 8;
		int maxLen = 12;

		char[] pwd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
		return new String(pwd);
	}

	private static final String[] specialNames = {
			"",
			" Thousand",
			" Million",
			" Billion",
			" Trillion",
			" Quadrillion",
			" Quintillion"
	};

	private static final String[] tensNames = {
			"",
			" Ten",
			" Twenty",
			" Thirty",
			" Forty",
			" Fifty",
			" Sixty",
			" Seventy",
			" Eighty",
			" Ninety"
	};

	private static final String[] numNames = {
			"",
			" One",
			" Two",
			" Three",
			" Four",
			" Five",
			" Six",
			" Seven",
			" Eight",
			" Nine",
			" Ten",
			" Eleven",
			" Twelve",
			" Thirteen",
			" Fourteen",
			" Fifteen",
			" Sixteen",
			" Seventeen",
			" Eighteen",
			" Nineteen"
	};

	private String convertLessThanOneThousand(int number) {
		String current;

		if (number % 100 < 20) {
			current = numNames[number % 100];
			number /= 100;
		} else {
			current = numNames[number % 10];
			number /= 10;

			current = tensNames[number % 10] + current;
			number /= 10;
		}
		if (number == 0)
			return current;

		String ret = numNames[number] + " Hundred";
		if (current.length() > 0)
			ret += " and" + current;

		return ret;
	}

	public String convert(int number) {

		if (number == 0) {
			return "zero";
		}

		String prefix = Constants.EMPTY_STRING;

		if (number < 0) {
			number = -number;
			prefix = "negative";
		}

		String current = Constants.EMPTY_STRING;
		int place = 0;

		do {
			int n = number % 1000;
			if (n != 0) {
				String s = convertLessThanOneThousand(n);
				current = s + specialNames[place] + current;
			}
			place++;
			number /= 1000;
		} while (number > 0);

		return (prefix + current).trim();
	}

	public String currencyNumberToWord(String number) {
		String sPaise = Constants.EMPTY_STRING;
		String[] split = number.split("\\.");
		int rupees = Integer.parseInt(split[0]);
		String sRupees = this.convert(rupees);
		if (rupees > 1) {
			sRupees += " Rupees";
		} else {
			sRupees += " Rupee";
		}

		if (split.length == 2) {
			int paise = Integer.parseInt(number.split("\\.")[1]);
			if (paise != 0) {
				sPaise += " and ";
				sPaise += this.convert(paise);
				sPaise += " Paise";
			}
		}

		return sRupees + sPaise + " Only.";
	}

	public List<Object> getObjectsList(List<?> list) {
		List<Object> objects = new ArrayList<>();
		objects.addAll(list);
		return objects;
	}

	public  String getObjectStringValue(Object obj){
		String objString = Constants.EMPTY_STRING;
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public String getObjectNullableStringValue(Object obj){
		return obj == null ? null :obj.toString();
	}

	/***************
	 public String generateChecksum(String path) throws Exception {

	 MessageDigest md = MessageDigest.getInstance("SHA1");
	 FileInputStream fis = new FileInputStream(path);
	 byte[] dataBytes = new byte[1024];

	 int nread = 0;

	 while ((nread = fis.read(dataBytes)) != -1) {
	 md.update(dataBytes, 0, nread);
	 }

	 byte[] mdbytes = md.digest();

	 // convert the byte to hex format
	 StringBuffer sb = new StringBuffer("");
	 for (int i = 0; i < mdbytes.length; i++) {
	 sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	 }

	 fis.close();

	 return sb.toString();
	 }
	 */



	public String getLeftJustified(String arg, int length) {
		String ret = Constants.EMPTY_STRING;
		if (arg != null) {

			if (arg.length() > length) {
				ret = arg.substring(0, length);
			} else {
				ret = new StringBuffer(arg).append(getPaddingString(length - arg.length())).toString();
			}
		} else {
			ret = getPaddingString(length);
		}
		return ret;
	}

	public String getRightJustified(String arg, int length) {
		String ret = Constants.EMPTY_STRING;
		if (arg != null) {

			if (arg.length() > length) {
				ret = arg.substring(0, length);
			} else {
				ret = new StringBuffer(getPaddingString(length - arg.length())).append(arg).toString();
			}
		} else {
			ret = getPaddingString(length);
		}
		return ret;
	}

	public String getCenterJustified(String arg, int length) {
		String ret = Constants.EMPTY_STRING;
		if (arg != null) {

			ret = new StringBuffer(getPaddingString((length - arg.length()) / 2)).append(arg).
					append(getPaddingString((length - arg.length()) / 2)).toString();
		} else {
			ret = getPaddingString(length);
		}
		return ret;
	}

	public String getPaddingString(int length) {
		StringBuffer sb = new StringBuffer();
		while (length > 0) {
			sb.append(Constants.SINGLE_SPACE);
			length--;
		}
		return sb.toString();
	}

	public String getLikeQueryString(String str) {
		final String var = isEmpty(str) ? Constants.EMPTY_STRING : str.trim().replace(Constants.PERCENTAGE, Constants.ESCAPE_PERCENTAGE).toUpperCase();
		return var + Constants.PERCENTAGE;
	}

	public String getContainLikeQueryString(String str) {
		final String var = isEmpty(str) ? Constants.EMPTY_STRING :  Constants.PERCENTAGE + str.trim().replace(Constants.PERCENTAGE, Constants.ESCAPE_PERCENTAGE).toUpperCase();
		return var + Constants.PERCENTAGE;
	}

	public String getTrimmedUpperCaseNoSpace(String batch) {
		batch = isEmpty(batch) ? Constants.EMPTY_STRING : batch;
		return batch.trim().replace(" ", Constants.EMPTY_STRING).toUpperCase();
	}

	public String getTrimmedUpperCase(String batch) {
		batch = isEmpty(batch) ? Constants.EMPTY_STRING : batch;
		return batch.trim().toUpperCase();
	}

	public String getNotNullableTrimmedUpperCase(String obj) throws DataFormatException {

		String objString;
		try {
			objString = obj.trim().toUpperCase();
		} catch (Exception e) {
			throw new DataFormatException(obj);
		}
		return objString;
	}

	public String getTrimmedStringValue(String obj){
		return getTrimmedStringValue(obj, Constants.EMPTY_STRING);
	}

	public String getTrimmedStringValue(String obj, String defaultStr){
		String objString = defaultStr;
		if (obj != null){
			objString = obj.trim();
		}
		return objString;
	}
	public String getSized(String str, int length) {
		String ret = "";
		if (str != null) {
			ret = (str.length() >= length) ? str.substring(0, length) : str;
		}
		return ret;
	}

	public String getCommaSeparatedCurrencyValue(Double value) {
		if (value < 1000) {
			return format("###.##", value);
		} else {
			double hundreds = value % 1000;
			int other = (int) (value / 1000);
			return format(",##.##", other) + ',' + format("000.##", hundreds);
		}
	}

	public String getCommaSeparatedTotalValue(Long value) {
		if (value < 1000) {
			return format("###", value);
		} else {
			double hundreds = value % 1000;
			int other = (int) (value / 1000);
			return format(",##", other) + ',' + format("000", hundreds);
		}
	}

	private String format(String pattern, Object value) {
		return new DecimalFormat(pattern).format(value);
	}


	public String getDefaultHyphen(JsonElement obj) {
		return (obj == null || isEmpty(obj.getAsString())) ? Constants.HYPHEN : obj.getAsString();
	}

	public boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	// TODO replace with isNull
	public boolean isEmpty(Object obj) {
		return obj == null;
	}

	public boolean isNull(Object obj) {
		return obj == null;
	}


	protected boolean isNotEqualToZero(Double d) {
		return d != 0.0;
	}

	protected boolean isNotEqualToZero(int i) {
		return i != 0;
	}

	protected boolean isNotEqualToZero(String s) {
		return !s.equals("0");
	}

	public Double round(Double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		Long factor = (long) Math.pow(10, places);
		value = value * factor;
		Long tmp = Math.round(value);
		return (Double.valueOf(tmp) / factor);
	}

	public LocalDate calculateDOB(String dateType, String dateValue) {

		LocalDate date = getCurrentDate();
		if (("Y").equals(dateType)) {
			String[] yearMonth = dateValue.split("\\.");

			Integer years = Integer.valueOf(yearMonth[0]);
			if (yearMonth.length > 1) {
				Double monthDays = Double.valueOf("0." + yearMonth[1]) * 12;
				years += (monthDays.intValue());
			}
			date = date.minusYears(years);

		} else if (("M").equals(dateType)) {
			date = date.minusMonths(Integer.valueOf(dateValue)+ 1);
		} else if (("D").equals(dateType)) {
			date = date.minusDays(Integer.valueOf(dateValue));
		}

		return date;
	}

	public String getMaskedPhoneNumberString(String message, String replace, String number, int last) {
		StringBuilder ret = new StringBuilder();
		int length = number.length();
		for (int i = 1; i <= length - last; i++) {
			ret.append("*");
		}
		ret.append(number.substring(length - last));
		return message.replace(replace, ret.toString());
	}

	public boolean isPhoneNumberFormat(String value) {
		boolean ret = false;
		if (value != null && value.length() >= 10) {
			ret = true;
			char[] array = value.toCharArray();
			for (int i = 0; i < value.length(); i++) {
				if (!Character.isDigit(array[i])) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}

	public String getNextSequence(String mnemonic, String code, String number, LocalDate date) {
		String financialYear = getFinancialYear(date);
		String prefix = mnemonic + "/" + code + "/" + financialYear + "/";
		String serial = "0";
		if (number != null) {
			String[] split = number.split("/");
			if (financialYear.equalsIgnoreCase(split[2])) {
				serial = split[3];
			}
		}
		return prefix + getNextSequence(serial);
	}

	private String getNextSequence(String number) {
		Long lNum = Long.parseLong(number) + 1;
		return String.format("%08d", lNum);
	}

	public boolean doesHavePropertyAccess(Long accessLevel, Long property) {
		ArrayList partition = new ArrayList();
		long tmp;
		if (accessLevel == 0) {
			partition.add(new Long(0));
		}

		for (int i = 0; i < 32; i++) {
			long mask = 1L << i;
			tmp = (mask & accessLevel);
			if (tmp != 0) partition.add(new Long(tmp));
		}
		return partition.contains(property);
	}

	public LocalDate getDaysAdjustedDate(int days) {
		return getDaysAdjustedDate(getCurrentDate(), days);
	}

	public LocalDate getDaysAdjustedDate(LocalDate date, int days) {
		return days < 0 ? date.minusDays(days * -1) : date.plusDays(days);
	}

	public String getExceptionTrace(Exception e) {
		String trace;
		try {
			trace = e.getMessage() + "->" + e.getStackTrace()[0].getFileName() + "->" + e.getStackTrace()[0].getClassName() + "-> " + e.getStackTrace()[0].getMethodName() + "->" + e.getStackTrace()[0].getLineNumber();
		} catch (Exception e1) {
			String msg = (e != null) ? e.toString() : "null";
			trace = "Error while getting exception trace -> " + msg;
		}
		return trace;
	}

	public String getAgeValue(Double obj){
		DecimalFormat format = new DecimalFormat("0.#");
		return format.format(obj);
	}

	public LocalDate getThirtyDays() {
		return getCurrentDate().plusDays(30);
	}

	public String getUpdateQuery(String tableName, List<String> fieldNames) {
		return  "UPDATE " + tableName + " SET " + getUpdateFields(fieldNames) + ", last_updated_by = :last_updated_by, last_updated_timestamp = :last_updated_timestamp";
	}

	private String getUpdateFields(List<String> fields) {
		StringBuilder fieldNames = new StringBuilder(fields.get(0)).append(" = :").append(fields.get(0));
		for (int i = 1; i < fields.size(); i++) fieldNames.append(", ").append(fields.get(i)).append(" = :").append(fields.get(i));
		return fieldNames.toString();
	}

	public Long getRandom(List<Long> ids) {
		Long id = null;
		if (ids != null && !ids.isEmpty()) {
			Random random = new Random();
			id = ids.get(random.nextInt(ids.size()));
		}
		return id;
	}

	public String getName(String first, String last) {
		return getName(first, null, last, Constants.SPACE_DELIMITER);
	}

	public String getName(String first, String middle, String last) {
		return getName(first, middle, last, Constants.SPACE_DELIMITER);
	}

	public String getName(String first, String middle, String last, String delimiter) {
		String name = Constants.EMPTY_STRING;
		if (!isEmpty(first)) name = name.concat(first);
		if (!isEmpty(middle)) {
			if(!isEmpty(name)) name = name.concat(delimiter);
			name = name.concat(middle);
		}
		if (!isEmpty(last)) {
			if(!isEmpty(name)) name = name.concat(delimiter);
			name = name.concat(last);
		}
		return name;
	}

	public <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> list = new ArrayList<>();
		if (iterable != null) {
			for (T anIterable : iterable) {
				list.add(anIterable);
			}
		}
		return list;
	}

	public String generate16DigitRandom(String prefix) {
		Random rand = new Random();

		long x = (long)(rand.nextDouble()*100000000000000L);

		return prefix + String.format("%014d", x);
	}

	//TODO add invoice number to nostro
	public String generateNostroReference(String invictusAccNumber) {
		String localDateTime = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmSS").format(getCurrentTime());
		return invictusAccNumber + "-" + localDateTime;
	}

	public String generateNostroReferenceForSettlements(Long aspirantId, Long partnerId, String invoiceNumber) {
		return aspirantId + Constants.HYPHEN + partnerId + Constants.HYPHEN + invoiceNumber;
	}

	public int getNumberOfQuarters() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) / 3;
	}

	public LocalDate getBeginOfYear() {
		return getCurrentDate().with(TemporalAdjusters.firstDayOfYear());
	}

	public int getNumberOfQuarters(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) / 3;
	}

	public int getNumberOfQuarters(Date fromDate, Date toDate) {
		return getNumberOfMonths(fromDate, toDate) / 3;
	}

	public int getNumberOfMonths(Date fromDate, Date toDate) {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.setTime(fromDate);
		to.setTime(toDate);
		int years = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
		return years * 12 + to.get(Calendar.MONTH) - from.get(Calendar.MONTH);
	}

	public int getNumberOfDays(LocalDate fromDate, LocalDate toDate) {
		return (int) Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();
	}

	public LocalDate convertToLocalDate(LocalDateTime dateToConvert) {
		return dateToConvert.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public int getAllowedValue(int value, int maxValue) {
		return ((value > maxValue) || (value == 0)) ? maxValue : value;
	}

	public int getMaxedValue(int value, int maxValue) {
		return Math.min(value, maxValue);
	}

	public boolean doesContain (int [] list, int value) {
		boolean ret = false;
		for (int aList : list) {
			if (aList == value) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public Double getNegativeValue(Double value) { return value * -1; }

	public int getNegativeValue(int value) { return value * -1; }

	public String getFileContentAsString(String fileLocation) throws IOException {

		Path path = Paths.get(fileLocation);

		return java.nio.file.Files.lines(path).collect(Collectors.joining());

	}

	public byte[] getBytes(String str) throws IOException {
		File file = new File(str);
		// File length
		int size = (int) file.length();
		if (size > Integer.MAX_VALUE) {
			System.out.println("File is too large");
		}
		byte[] bytes = new byte[size];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		int read = 0;
		int numRead = 0;
		while (read < bytes.length && (numRead = dis.read(bytes, read, bytes.length - read)) >= 0) {
			read = read + numRead;
		}
		dis.close();

		return bytes;
	}

	public String getDomainName(String email) {
		return email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
	}


	public int getCompensationDays(String compensationCycle) {
		int days;
		if (isEmpty(compensationCycle)) {
			days = 30;
		} else {
			switch (compensationCycle) {
				case "D": days = 1; break;
				case "W": days = 7; break;
				case "F": days = 15; break;
				case "M": days = 30; break;
				default: days = 30;
			}
		}
		return days;
	}

	public Double getNameMatchPercentage(String payload, String name) {
		Double retVal;
		Double score = 0.0;

		String[] nameSplit = name.split(" ");
		String[] payloadSplit = payload.split(" ");

		for (String val : nameSplit) {
			for (String payloadVal : payloadSplit) {
				if (val.equalsIgnoreCase(payloadVal)) {
					score++;
					break;
				}
			}
		}

		retVal = (score/payloadSplit.length) * 100;
		return retVal;
	}


	public Double getRoundValue(String val) throws DataFormatException {
		Double lId = 0.0;
		try {
			if (val != null && val.trim().length() > 0) lId = Double.valueOf(Math.round(Double.valueOf(val)));
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

	public Double getRoundValue(Double value){
		return value == null ? 0.0 : Math.round(Double.valueOf(value));
	}

	public Double getRoundValue2(Double value){
		return value == null ? 0.0 : Math.round(Double.valueOf(value) * 100.0) / 100.0; // return .0 so that division result is decimal
	}


	public String getDateTimeSplitString(LocalDateTime dateTime, String middleString) throws ParseException {
		String dateTimeString = "";
		if (!isEmpty(dateTime)) {
			dateTimeString = convertTimeToString(dateTime);
			String[] splitStr = dateTimeString.split(" ");
			String splitZero = convertYYYYMMDDtoDDMMYYYY(splitStr[0]);
			String milliSecond = splitStr[1];
			String removeMilliSeconds = milliSecond.substring(0, milliSecond.length() - 3);
			dateTimeString = splitZero + " " + middleString + " " + removeMilliSeconds;
		}
		return dateTimeString;
	}
	public Double getDouble2(Double val) {
		return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public Double getDoubleValue(String val) throws DataFormatException {
		Double lId;
		try {
			lId = Double.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}
	public Double getDouble2Value(double value) {
		return (double) ((int) Math.ceil(value) );
	}


	public double getDouble(JsonElement obj) {
		Double ret = 0.0;
		if (obj != null) {
			ret = obj.getAsDouble();
		}
		return ret;

	}

	public double getDouble(Object obj) {
		Double ret = 0.0;
		if (obj != null) {
			ret = (double) obj;
		}
		return ret;
	}

	public Double getNullableDouble(Object obj) {
		Double ret = null;
		if (obj != null) {
			ret = (Double) obj;
		}
		return ret;
	}


	public Double getNotNullDoubleValue(String val) throws DataFormatException {
		Double lId = 0.0;
		try {
			if (val != null && val.trim().length() > 0) lId = Double.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

	public Double getNullableDoubleValue(String val) throws DataFormatException {
		Double lId = null;
		try {
			if (val != null && val.trim().length() > 0) lId = Double.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

	public Double getNotNullDoubleValue(Double val){
		return val == null ? 0.0 : val;
	}

	public double getDoubleFromString(String val) {
		double ret = 0.0;
		if (val != null) {
			ret = new BigDecimal(val).doubleValue();
		}
		return ret;
	}

	//    public Double getNullableDoubleValue(String val) throws DataFormatException {
//        Double lId = null;
//        try {
//            if (val != null && val.trim().length() > 0) lId = Double.valueOf(val);
//        } catch (Exception e) {
//            throw new DataFormatException(val);
//        }
//        return lId;
//    }
//
//    public Double getNullableDoubleValue(Double val) {
//         return val == null ? 0.0 : val;
//    }

	public String getDoubleValueForAPK(String arg) {
		String ret = "0.00";
		if (arg != null && arg.trim().length() > 0 && !".".equals(arg)) {
			try {
				ret = String.format("%.2f", Double.parseDouble(arg));
			} catch (Exception e) {

			}
		}
		return ret;
	}

	public String getDouble1StringValue(Double obj) {
		String objString = "0.0";
		if (obj != null) {
			objString = String.format("%.1f", obj);
		}
		return objString;
	}

	public String getDouble2StringValue(Double obj) {
		String objString = "0.00";
		if (obj != null) {
			objString = String.format("%.2f", obj);
		}
		return objString;
	}

	public String getDoubleStringValue(Double obj){
		String objString = "0.000";
		if (obj != null){
			objString = String.format( "%.3f", obj );
		}
		return objString;
	}

	public String convertYYYYMMDDtoDDMMYYYY(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return localDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
	}

	public Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	public Integer getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	public Integer getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public Timestamp getCurrentTimestamp() {
		return Timestamp.valueOf(getCurrentTime());
	}

	public int getDigitAtGivenPlace(int num, int place) {
		int reversed = 0;
		while(num != 0) {
			int digit = num % 10;
			reversed = reversed * 10 + digit;
			num /= 10;
		}

		int divident = 1;
		while ( place > 1) {
			divident = divident*10;
			place--;
		}
		return (reversed / divident) % 10;
	}

	public List<String> getMessages(String message) {
		List<String> msg = new ArrayList<>();
		msg.add(message);
		return  msg;
	}

	public String getString(Object obj) {
		String ret = Constants.EMPTY_STRING;
		if (obj != null) {
			ret = obj.toString();
		}
		return ret;

	}

	public String getString(JsonElement obj) {
		String ret = Constants.EMPTY_STRING;
		if (obj != null) {
			ret = obj.getAsString();
		}
		return ret;
	}

	//TODO - Replace below with getTrimmedStringValue(String)
	public String getStringValue(String obj){
		String objString = Constants.EMPTY_STRING;
		if (obj != null){
			objString = obj;
		}
		return objString;
	}

	protected String getStringValueWithQuotes(String str) {
		return "'" + str + "'";
	}

	public String getNullableString(Object obj) {
		String ret = null;
		if (obj != null) {
			ret = obj.toString();
		}
		return ret;
	}

	public String getNullableString(JsonElement obj) {
		String ret = null;
		if (obj != null) {
			ret = obj.getAsString();
		}
		return ret;
	}

	public String getNullableString(JsonObject obj, String element) {
		String ret = null;
		if (obj != null && obj.has(element)) {
			ret = obj.get(element).getAsString();
		}
		return ret;
	}


	public int getInt(JsonElement obj) {
		int ret = 0;
		if (obj != null) {
			ret = obj.getAsInt();
		}
		return ret;
	}

	public int getInt(Object obj) {
		int ret = 0;
		if (obj != null) {
			ret = (int) obj;
		}
		return ret;
	}
	public Integer getIntegerValue(String val) throws DataFormatException {
		Integer lId;
		try {
			lId = Integer.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}

		return lId;
	}

	public Integer getNullableIntegerValue(String val) throws DataFormatException {
		Integer lId = null;
		try {
			if (val != null && val.trim().length() > 0) lId = Integer.valueOf((val));
		} catch (Exception e) {
			throw new DataFormatException(val);
		}
		return lId;
	}

	public Integer getNullableIntegerValue(Integer val) throws DataFormatException {
		return val == null ? 0 : val;
	}

	public Integer getNotNullIntegerValue(String val) throws DataFormatException {
		Integer lId = 0;
		try {
			if (val != null && val.trim().length() > 0) lId = Integer.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}

		return lId;
	}


	public Integer getNotNullIntegerValue(Integer val) {
		return val == null ? 0 : val;
	}

	public Integer getNullableInt(Object obj) {
		Integer ret = null;
		if (obj != null) {
			ret = (Integer) obj;
		}
		return ret;
	}

	public String getIntegerStringValue(Integer obj){
		String objString = "0";
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public String getNullableIntegerStringValue(Integer obj){
		String objString = null;
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public int getIntFromString(String value) {
		int ret = 0;
		if (value != null) {
			ret = Integer.getInteger(value);
		}
		return ret;
	}


	public long getLong(JsonElement obj) {
		Long ret = 0L;
		if (obj != null) {
			ret = obj.getAsLong();
		}
		return ret;
	}

	public long getLong(String obj) {
		long ret = 0L;
		if (obj != null) {
			ret = Long.parseLong(obj);
		}
		return ret;
	}

	public long getLong(Object obj) {
		Long ret = 0L;
		if (obj != null) {
			ret = (long)obj;
		}
		return ret;
	}

	public Long getLongValue(String val) throws DataFormatException {
		Long lId;
		try {
			lId = Long.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}

		return lId;
	}

	public Long getNullableLong(Object obj) {
		Long ret = null;
		if (obj != null) {
			ret = (long) obj;
		}
		return ret;
	}

	public Long getNullableLongValue(String val) throws DataFormatException {
		Long lId = null;
		try {
			if (val != null && val.trim().length() > 0) lId = Long.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}

		return lId;
	}

	public Long getNullableLongValue(Long val) throws DataFormatException {
		return val == null ? 0 : val;
	}

	public String getLongStringValue(Long obj){
		String objString = "0";
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public String getNullableLongStringValue(Long obj){
		String objString = null;
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public Short getShortValue(String val) throws DataFormatException {
		Short lId;
		try {
			lId = Short.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}

		return lId;
	}

	public String getShortStringValue(Short obj){
		String objString = "0";
		if (obj != null){
			objString = obj.toString();
		}
		return objString;
	}

	public Short getNullableShortValue(String val) throws DataFormatException {
		Short lId = 0;
		try {
			if (val != null && val.trim().length() > 0) lId = Short.valueOf(val);
		} catch (Exception e) {
			throw new DataFormatException(val);
		}

		return lId;
	}

	public Short getNullableShortValue(Short val) throws DataFormatException {
		return val == null ? 0 : val;
	}

	public BigDecimal getBigDecimal(JsonElement obj) {
		BigDecimal ret = new BigDecimal("0.00");
		if (obj != null) {
			ret = obj.getAsBigDecimal();
		}
		return ret;
	}

	public BigDecimal getBigDecimal(int value) {
		return new BigDecimal(value);
	}

	public BigDecimal getBigDecimal(Object obj) {
		BigDecimal ret = new BigDecimal(0.0);
		if (obj != null) {
			ret = (BigDecimal) obj;
		}
		return ret;
	}
	public BigDecimal getNullableBigDecimal(Object obj) {
		BigDecimal ret = null;
		if (obj != null) {
			ret = (BigDecimal) obj;
		}
		return ret;
	}

	public static String getUUID() {
		UUID requestUUID = Generators.randomBasedGenerator().generate();
		return requestUUID.toString();
	}

	public String getRandomUUID() {
		return UUID.randomUUID().toString();
	}

	public boolean isNotEmptyJsonStr(JsonElement element) {
		return (element != null && !isEmpty(element.getAsString()));
	}

	public Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		//gsonBuilder.serializeNulls();

		final DateFormat timestampFormat;
		timestampFormat = new SimpleDateFormat(Constants.SQL_TIMESTAMP_FORMAT);
		timestampFormat.setTimeZone(TimeZone.getTimeZone(ZONE_ID));
		gsonBuilder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>() {
			@Override
			public JsonElement serialize(Timestamp date, Type typeOfSrc, JsonSerializationContext context) {
				// convert date to long
				return new JsonPrimitive(timestampFormat.format(date));
			}
		});
		gsonBuilder.registerTypeAdapter(Timestamp.class, new JsonDeserializer() {
			@Override
			public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
					JsonParseException {
				try {
					return new Timestamp(timestampFormat.parse(json.getAsString()).getTime());
				} catch (Exception e) {
					return null;
				}
			}
		});

		final DateFormat timestampFormatMilli;
		timestampFormatMilli = new SimpleDateFormat(Constants.SQL_TIMESTAMP_MILLI_FORMAT);
		timestampFormatMilli.setTimeZone(TimeZone.getTimeZone(ZONE_ID));
		gsonBuilder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>() {
			@Override
			public JsonElement serialize(Timestamp date, Type typeOfSrc, JsonSerializationContext context) {
				// convert date to long
				return new JsonPrimitive(timestampFormatMilli.format(date));
			}
		});
		gsonBuilder.registerTypeAdapter(Timestamp.class, new JsonDeserializer() {
			@Override
			public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
					JsonParseException {
				try {
					return new Timestamp(timestampFormatMilli.parse(json.getAsString()).getTime());
				} catch (Exception e) {
					return null;
				}
			}
		});

		final DateFormat dateFormat;
		dateFormat = new SimpleDateFormat(Constants.SQL_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(ZONE_ID));
		gsonBuilder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

			@Override
			public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
				// convert date to long
				return new JsonPrimitive(dateFormat.format(date));
			}
		});
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
					JsonParseException {
				try {
					return dateFormat.parse(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}
		});

		final DateFormat localDateFormat;
		localDateFormat = new SimpleDateFormat(Constants.SQL_DATE_FORMAT);
		localDateFormat.setTimeZone(TimeZone.getTimeZone(ZONE_ID));
		gsonBuilder.registerTypeAdapter(LocalDate.class,
						(JsonSerializer<LocalDate>) (localDate, type, context)
								-> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
				.registerTypeAdapter(LocalDate.class,
						(JsonDeserializer<LocalDate>) (jsonElement, type, context)
								-> LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));


		final DateFormat localDateTimeFormat;
		localDateTimeFormat = new SimpleDateFormat("d::MMM::uuuu HH::mm::ss");
		localDateTimeFormat.setTimeZone(TimeZone.getTimeZone(ZONE_ID));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.SQL_TIMESTAMP_FORMAT);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@SneakyThrows
			@Override
			public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
				return new JsonPrimitive(formatter.format(localDateTime));
			}
		});
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				LocalDateTime dateTime = null;
				try {
					dateTime = LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(Constants.SQL_TIMESTAMP_FORMAT));
				} catch (Exception e) {
					dateTime = LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(Constants.SQL_TIMESTAMP_MILLI_FORMAT));
				}
				return dateTime;
			}

		}).create();

		return gsonBuilder
				//			.excludeFieldsWithoutExposeAnnotation()

				//            .setExclusionStrategies( new HiddenAnnotationExclusionStrategy())
				.addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						final Expose expose = fieldAttributes.getAnnotation(Expose.class);
						return expose != null && !expose.serialize();
					}

					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						return false;
					}
				})
				.addDeserializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						final Expose expose = fieldAttributes.getAnnotation(Expose.class);
						return expose != null && !expose.deserialize();
					}

					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						return false;
					}
				})            .create();
	}

	@SneakyThrows
	public String objectToString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	@SneakyThrows
	public JsonObject getJsonObject(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		Gson gson = getGson();
		String valueAsString = mapper.writeValueAsString(object);
		return gson.fromJson(valueAsString, JsonObject.class);
	}

	public JsonObject getJsonObject(String value) {
		return getGson().fromJson(value, JsonObject.class);
	}

	public JsonObject toJsonObjectTree(Object src, Type type) {
		return (JsonObject) getGson().toJsonTree(src, type);
	}

	public JsonArray toJsonArrayTree(Object src, Type type) {
		return (JsonArray) getGson().toJsonTree(src, type);
	}

	public JsonArray toJsonArrayTree(List<String> src) {
		TypeToken<List<String>> typeToken = new TypeToken<>() {};
		return (JsonArray) getGson().toJsonTree(src, typeToken.getType());
	}

	public String toJson(Object obj) {
		return getGson().toJson(obj);
	}

	public String getNotJsonNullString(JsonElement obj) {
		String ret = Constants.EMPTY_STRING;
		if (obj != null && !obj.isJsonNull()) {
			ret = obj.getAsString();
		}
		return ret;
	}
	//TODO move to DateTimeHelper
	public LocalDateTime getStartOfDay(String day) {
		LocalDate dt = getLocalDate(day);
		return dt.atTime(LocalTime.MIN);
	}
	public LocalDateTime getEndOfDay(String day) {
		LocalDate dt = getLocalDate(day);
		return dt.atTime(LocalTime.MAX);
	}

	// Traverse the json path and return the value
	public String getValueFromComplexJson(String jsonPath, JsonObject jsonObject) {

		if (jsonPath.isEmpty()) throw new IllegalArgumentException("jsonPath is empty");
		if (jsonObject.isJsonNull() || jsonObject.size() == 0) throw new IllegalArgumentException("invalid jsonObject");

		String[] jsonPaths = jsonPath.split("\\.");
		String value = Constants.EMPTY_STRING;

		for (String jKey : jsonPaths) {
			if (jKey.contains("[") && jKey.contains("]")) {
				int index = Integer.parseInt(jKey.substring(jKey.indexOf('[') + 1, jKey.indexOf(']')));
				jKey = jKey.substring(0, jKey.indexOf('['));
				if (!jsonObject.has(jKey)) {
					return Constants.EMPTY_STRING;
				} else {
					if(jsonObject.get(jKey).isJsonArray()) {
						jsonObject = jsonObject.getAsJsonArray(jKey).get(index).getAsJsonObject();
					}
				}
			} else {
				if (!jsonObject.has(jKey)) {
					return Constants.EMPTY_STRING;
				} else {
					if(jsonObject.get(jKey).isJsonObject()) {
						jsonObject = jsonObject.getAsJsonObject(jKey);
					} else if (jsonObject.get(jKey).isJsonPrimitive()) {
						value = jsonObject.get(jKey).getAsString();
					}
				}
			}
		}
		return value;
	}

	public JsonObject getNullProcessedData(JsonObject jsonObject) {
		JsonObject row = new JsonObject();
		if (!isEmpty(jsonObject)) {
			jsonObject.keySet().forEach(keyStr ->
			{
				String startsWith = keyStr.substring(0, 2);
				switch (startsWith) {
					case "t_":
					case "d_": {
						String value = jsonObject.get(keyStr).getAsString();
						if (isEmpty(value) || "null".equalsIgnoreCase(value)) {
							row.add(keyStr, JsonNull.INSTANCE);
						} else {
							row.addProperty(keyStr, value);
						}
						break;
					}
					default: {
						row.add(keyStr, jsonObject.get(keyStr));
					}
				}
			});
		}
		return row;
	}

	public String getC2Code(Map<String, String> headers) {
		return headers.get(Constants.HEADER_C2CODE);
	}

	public String getBr2Code(Map<String, String> headers) {
		return headers.get(Constants.HEADER_BRCODE);
	}

	public Long getUserId(Map<String, String> headers) {
		return getLong(headers.get(Constants.HEADER_USER_ID));
	}

	public String getLocale(Map<String, String> headers) {
		String locale = getString(headers.get(Constants.HEADER_LOCALE));
		return isEmpty(locale) ? Constants.LANGUAGE_ENGLISH : locale;
	}

	public boolean allCharactersSame(String s)
	{
		int n = s.length();
		final char charAt = s.charAt(0);
		for (int i = 1; i < n; i++) {
			if (s.charAt(i) != charAt)
				return false;
		}
		return true;
	}

	public void validateNotEmptyData(String input, String fieldName) throws InputPayloadException {
		if (isEmpty(input))
			throw new InputPayloadException(fieldName, "Cannot not be empty!");
	}

	public void validateDataLength(String input, String fieldName, Integer min, Integer max) throws InputPayloadException {
		if (min != null && getString(input).length() < min)
			throw new InputPayloadException(fieldName, "Minimum length is " + min + "!");
		if (max != null && getString(input).length() > max)
			throw new InputPayloadException(fieldName, "Maximum length is " + max + "!");
	}

	public String getMongoContainsQueryParameter(String str) {
		return ".*" + getMongoStartQueryParameter(str);
	}

	public String getMongoStartQueryParameter(String str) {
		return "^" + str.trim()
				.replaceAll("\\*", " ")
				.replaceAll("-", ".*")
				.replaceAll(" ", ".*") + ".*";
	}

	public String getMongoSearchParameter(String str) {
		return (str.startsWith(" ") ? getMongoContainsQueryParameter(str) : getMongoStartQueryParameter(str));
	}

	public void publishEvent(String connectionString, String eventHubName, List<EventData> allEvents ) {
		EventHubUtil.publishEvents(connectionString, eventHubName, allEvents);
	}

	public String getMonthlyCollectionName(String collectionName) {
		return String.format("%s-%s%s",
				collectionName.toLowerCase(),
				getCurrentTime().getYear(),
				getCurrentTime().getMonthValue());
	}

	public long diffInSeconds(LocalDateTime from, LocalDateTime to) {
		return ChronoUnit.SECONDS.between(from, to);
	}

	public long diffInMinutes(LocalDateTime from, LocalDateTime to) {
		return ChronoUnit.MINUTES.between(from, to);
	}

	public long diffInHours(LocalDateTime from, LocalDateTime to) {
		return ChronoUnit.HOURS.between(from, to);
	}

	public long diffInDays(LocalDateTime from, LocalDateTime to) {
		return ChronoUnit.DAYS.between(from, to);
	}
}
