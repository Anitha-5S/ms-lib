package com.c2.lc.lib.api;


import com.c2.lc.lib.utils.Constants;

import java.io.Serializable;

public class MsHeaderParams implements Serializable {

	private static final long serialVersionUID = 4179602005912699768L;

    private String baseURL = Constants.HYPHEN;

	private String appId = Constants.HYPHEN;
	private String loginId = Constants.HYPHEN;
	private String partnerId = Constants.HYPHEN;
	private String authToken = Constants.HYPHEN;
	private String ipAddress = Constants.HYPHEN;
	private String deviceId = Constants.HYPHEN;
	private String latitude = Constants.HYPHEN;
	private String longitude = Constants.HYPHEN;
	private String classification = Constants.HYPHEN;

	private String host = Constants.HYPHEN;
	private String accept = Constants.HYPHEN;
	private String acceptEncoding = Constants.HYPHEN;
	private String acceptLanguage = Constants.HYPHEN;
	private String contentType = Constants.HYPHEN;
	private String origin = Constants.HYPHEN;
	private String referer = Constants.HYPHEN;
	private String userAgent = Constants.HYPHEN;
	private String connection = Constants.HYPHEN;
	private String forwardPort = Constants.HYPHEN;
	private String forwardProto = Constants.HYPHEN;
	private String forwardHost = Constants.HYPHEN;
	private String forwardServer = Constants.HYPHEN;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getForwardPort() {
        return forwardPort;
    }

    public void setForwardPort(String forwardPort) {
        this.forwardPort = forwardPort;
    }

    public String getForwardProto() {
        return forwardProto;
    }

    public void setForwardProto(String forwardProto) {
        this.forwardProto = forwardProto;
    }

    public String getForwardHost() {
        return forwardHost;
    }

    public void setForwardHost(String forwardHost) {
        this.forwardHost = forwardHost;
    }

    public String getForwardServer() {
        return forwardServer;
    }

    public void setForwardServer(String forwardServer) {
        this.forwardServer = forwardServer;
    }
}
