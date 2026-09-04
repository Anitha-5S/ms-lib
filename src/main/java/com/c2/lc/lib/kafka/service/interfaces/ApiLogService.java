package com.c2.lc.lib.kafka.service.interfaces;

import com.c2.lc.lib.kafka.ApiLogTopic;
import com.c2.lc.lib.kafka.LtApiLogTopic;
import com.c2.lc.lib.services.interfaces.BaseService;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public interface ApiLogService extends BaseService {
    boolean postToTopic(ApiLogTopic logTopic, Properties producerProperties) throws ExecutionException, InterruptedException;

    boolean postToTopic(LtApiLogTopic logTopic, Properties producerProperties) throws ExecutionException, InterruptedException;
}
