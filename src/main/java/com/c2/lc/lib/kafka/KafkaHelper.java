package com.c2.lc.lib.kafka;

import com.c2.lc.lib.exceptions.CommunicationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class KafkaHelper {

    //TODO : remove duplicate code
    public boolean postToTopic(String topicName, Properties properties, Object topicData) throws ExecutionException, InterruptedException {
        Producer<String, Object> kafkaProducer = new KafkaProducer<>(properties);
        RecordMetadata recordMetadata = kafkaProducer.send(new ProducerRecord<>(topicName, topicData)).get();
        kafkaProducer.close();
        return recordMetadata != null;
    }

    public void produceAsyncMessage(KafkaTemplate<String, Object> kafkaTemplate, String topicName, String topicId, Object message) {
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(topicName, message);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable e) {
                log.error("Failed  - Id `{}`, Exception - {}", topicId, e.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.debug("Success - Id `{}`", topicId);
            }
        });
    }

    public void produceSyncMessage(KafkaTemplate<String, Object> kafkaTemplate, String topicName, String topicId, Object message) throws CommunicationErrorException {
        try {
            RecordMetadata recordMetadata = kafkaTemplate.send(topicName, message).get().getRecordMetadata();
            if (recordMetadata == null) {
                log.error("Failed  - Id `{}`, Exception - Failed to send message", topicId);
            } else {
                log.debug("Id `{}` Success", topicId);
            }
        } catch (Exception e) {
            log.error("Failed  - Id `{}`, Exception - {}", topicId, e.getMessage());
            throw new CommunicationErrorException("Failed to post data ", e.getMessage());
        }
    }

}
