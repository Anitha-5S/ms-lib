package com.c2.lc.lib.kafka.service;


import com.c2.lc.lib.kafka.ApiRequestData;
import com.c2.lc.lib.kafka.ApiRequestTopic;
import com.c2.lc.lib.kafka.KafkaHelper;
import com.c2.lc.lib.kafka.LtApiRequestTopic;
import com.c2.lc.lib.kafka.service.interfaces.ProducerService;
import com.c2.lc.lib.services.BaseServicesImpl;
import com.c2.lc.lib.utils.SystemHelper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class ProducerServiceImpl extends BaseServicesImpl implements ProducerService {

    @Override
    public boolean postToTopic(String topicName, String uri, Properties properties, ApiRequestData topicData) throws ExecutionException, InterruptedException {
        ApiRequestTopic topic = new ApiRequestTopic();
        topicData.setUri(uri);
        topic.setTopicId(SystemHelper.getUUID());
        topic.setTopicData(topicData);
        topic.setDatetime(helper.getCurrentTimeString());
        return new KafkaHelper().postToTopic(topicName, properties, topic);
    }

    @Override
    public boolean postToTopic(String topicName, String uri, Properties properties, ApiRequestData topicData, String topicId, Timestamp startTime, String debugToken) throws ExecutionException, InterruptedException {
        LtApiRequestTopic topic = new LtApiRequestTopic();
        topicData.setUri(uri);
        topic.setTopicId(topicId);
        topic.setTopicData(topicData);
        topic.setXDebugToken(debugToken);
        topic.setProducerStartAt(startTime);
        topic.setDatetime(helper.getCurrentTimeString());
        return new KafkaHelper().postToTopic(topicName, properties, topic);
    }

}
