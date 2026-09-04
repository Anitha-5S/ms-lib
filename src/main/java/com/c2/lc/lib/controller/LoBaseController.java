package com.c2.lc.lib.controller;

import com.c2.lc.lib.bo.LcHeaderBO;
import com.c2.lc.lib.exceptions.InvalidRequestException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class LoBaseController extends BaseController {

    private static final String FIRM_ID = "x-csquare-firm-id";
    private static final String C2CODE = "x-csquare-c2-code";
    private static final String BRCODE = "x-csquare-br-code";
    private static final String TERMINAL_ID = "x-csquare-terminal-id";
    private static final String TYPE = "x-csquare-type";

    protected LcHeaderBO getLcHeader(Map<String, String> headers) throws InvalidRequestException {
        log.debug("Headers -. {}", headers.toString());
        String firmId = headers.get(FIRM_ID);
        String c2Code = headers.get(C2CODE);
        String brCode = headers.get(BRCODE);
        String terminalId = headers.get(TERMINAL_ID);

        if (helper.isEmpty(terminalId) && helper.isEmpty(c2Code) && helper.isEmpty(brCode) ) {
            throw new InvalidRequestException("", "Invalid headers!");
        }
        return new LcHeaderBO(helper.getLong(headers.get(TERMINAL_ID)), helper.getLong(firmId),
                c2Code, brCode, headers.get(TERMINAL_ID), headers.get(TYPE));
    }

/*    protected JsonObject getPaginatedResponse(SearchBO searchBO, JsonArray jsonArray, long count) {
        JsonObject data = new JsonObject();
        data.add("j_list", jsonArray);
        data.addProperty("n_next_page", searchBO.getPage() + 1);
        data.addProperty("n_total", count);
        return data;
    }*/

    //TODO rename as JList
    protected JsonObject getPaginatedResponse(JsonArray jsonArray) {
        JsonObject data = new JsonObject();
        data.add("j_list", jsonArray);
        return data;
    }

}
