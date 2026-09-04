package com.c2.lc.lib.kafka.service;

import com.c2.lc.lib.kafka.ApiLogTopic;
import com.c2.lc.lib.kafka.LtApiLogTopic;
import com.c2.lc.lib.kafka.service.interfaces.ApiLogService;
import com.c2.lc.lib.services.BaseServicesImpl;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class ApiLogServiceImpl extends BaseServicesImpl implements ApiLogService {

    @Override
    public boolean postToTopic(ApiLogTopic logTopic, Properties producerProperties) throws ExecutionException, InterruptedException {
        Producer<String, Object> kafkaProducer = new KafkaProducer<>(producerProperties);
        RecordMetadata recordMetadata = kafkaProducer.send(
                new ProducerRecord<>(logTopic.getTopicData().getTopicName(), logTopic)
        ).get();
        kafkaProducer.close();
        return recordMetadata != null;
    }

    @Override
    public boolean postToTopic(LtApiLogTopic logTopic, Properties producerProperties) throws ExecutionException, InterruptedException {
        Producer<String, Object> kafkaProducer = new KafkaProducer<>(producerProperties);
        RecordMetadata recordMetadata = kafkaProducer.send(
                new ProducerRecord<>(logTopic.getTopicData().getTopicName(), logTopic)
        ).get();
        kafkaProducer.close();
        return recordMetadata != null;
    }

}
