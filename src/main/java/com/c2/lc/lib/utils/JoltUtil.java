package com.c2.lc.lib.utils;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtilImpl;
import com.bazaarvoice.jolt.JsonUtils;

import java.util.List;

public class JoltUtil {

/*    public String jsonToJson(String sourceJson, String specFile) throws FileNotFoundException {
        InputStream specStream = new FileInputStream(new File(specFile));
        return jsonToJson(sourceJson, specStream);
    }
*/

/*
    public String jsonToJson(String sourceJson, InputStream specStream) {
        JsonUtilImpl jsonUtil = new JsonUtilImpl();
        List<Object> jsonToList = jsonUtil.jsonToList(specStream);
        Chainr chainr = Chainr.fromSpec(jsonToList);

        Object inputJSON = jsonUtil.jsonToObject(sourceJson);

        Object transformedOutput = chainr.transform(inputJSON);
        return JsonUtils.toJsonString(transformedOutput);
    }
*/

    public String jsonToJson(String sourceJson, String specStream) {
        JsonUtilImpl jsonUtil = new JsonUtilImpl();
        List<Object> jsonToList = jsonUtil.jsonToList(specStream);
        Chainr chainr = Chainr.fromSpec(jsonToList);

        Object inputJSON = jsonUtil.jsonToObject(sourceJson);

        Object transformedOutput = chainr.transform(inputJSON);
        return JsonUtils.toJsonString(transformedOutput);
    }
}
