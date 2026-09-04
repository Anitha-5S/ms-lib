package com.c2.lc.lib.kafka.service.interfaces;

import com.c2.lc.lib.kafka.ApiRequestTopic;
import com.c2.lc.lib.kafka.LtApiRequestTopic;
import com.c2.lc.lib.services.interfaces.BaseService;
import com.google.gson.JsonObject;

public interface ConsumerService extends BaseService {
    JsonObject callApi(String baseUrl, ApiRequestTopic topicData, int timeOut);

    JsonObject callApi(String baseUrl, LtApiRequestTopic topicData, int timeOut);
}
