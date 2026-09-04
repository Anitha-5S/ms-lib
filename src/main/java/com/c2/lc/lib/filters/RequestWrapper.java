
package com.c2.lc.lib.filters;

import com.c2.lc.lib.exceptions.InvalidRequestException;
import com.c2.lc.lib.filters.interfaces.ApiFilterService;
import com.c2.lc.lib.utils.Constants;
import com.c2.lc.lib.utils.SystemHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public RequestWrapper(HttpServletRequest request, ApiFilterService apiFilterService, SystemHelper helper, boolean doFilter) throws Exception {
        // So that other request method behave just like before
        super(request);

        body = updateRequestBody(request, apiFilterService, helper, doFilter);
    }

    private String updateRequestBody(HttpServletRequest request, ApiFilterService apiFilterService, SystemHelper helper, boolean doFilter) throws Exception {
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // no key available here to authenticate
        if (!doFilter
                || request.getRequestURI().equals("/eco/lc/profile")
                || request.getRequestURI().equals("/eco/lc/ecoprofile")
                || request.getRequestURI().endsWith(Constants.APP_PING_URI)) {
            return body;
        }

        String apiKey = request.getHeader("X-csquare-api-key");
        if (apiKey == null)
            throw new InvalidRequestException("", "api key missing!");
        log.debug("Lib Key:{}", apiKey);

        JsonObject payload = helper.getJsonObject(body);
        String [] keys = apiFilterService.getDecryptedKeyValues(apiKey);
        if (keys.length != 4 && keys.length != 5)
            throw new InvalidRequestException("", "Invalid api key!");

        String c2Code = keys[0];
        String brCode = keys[1];

        log.debug("Lib c2Code:{}", c2Code);
        log.debug("Lib brCode:{}", brCode);
        log.debug("Lib pre filter {}", helper.toJson(payload));
        // update root
        if (payload.has("c2_code"))
            payload.addProperty("c2_code", c2Code);
        if (payload.has("br_code"))
            payload.addProperty("br_code", brCode);

        JsonObject data = payload.get("data").getAsJsonObject();

        // update mst
        if (data.has("mst")) {
            JsonObject mst = data.get("mst").getAsJsonObject();
            if (mst.has("c_br_code"))
                mst.addProperty("c_br_code", brCode);
        }

        // update det
        if (data.has("det")) {
            JsonArray det = data.get("det").getAsJsonArray();
            if (det.size() > 0) {
                for (JsonElement element : det) {
                    JsonObject json = element.getAsJsonObject();
                    if (json.has("c_br_code"))
                        json.addProperty("c_br_code", brCode);
                }
            }
        }

        // rows
        if (data.has("rows")) {
            JsonArray rows = data.get("rows").getAsJsonArray();
            if (rows.size() > 0) {
                for (JsonElement element : rows) {
                    JsonObject json = element.getAsJsonObject();
                    if (json.has("c_br_code"))
                        json.addProperty("c_br_code", brCode);
                }
            }
        }

        log.debug("Lib post filter {}", helper.toJson(payload));
        return helper.toJson(payload);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}


