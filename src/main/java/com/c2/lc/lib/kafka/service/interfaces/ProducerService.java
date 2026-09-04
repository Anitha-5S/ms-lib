package com.c2.lc.lib.kafka.service.interfaces;

import com.c2.lc.lib.kafka.ApiRequestData;
import com.c2.lc.lib.services.interfaces.BaseService;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public interface ProducerService extends BaseService {

    boolean postToTopic(String topicName, String uri, Properties properties, ApiRequestData topicData) throws ExecutionException, InterruptedException;

    boolean postToTopic(String topicName, String uri, Properties properties, ApiRequestData topicData, String topicId, Timestamp startTime, String debugToken) throws ExecutionException, InterruptedException;
}
