package com.c2.lc.lib.configuration;

import com.c2.lc.lib.topics.MsApiTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

	@Value("${kafka.common.BOOTSTRAP_SERVERS_CONFIG}")
    private String BOOTSTRAP_SERVERS_CONFIG;

	@Value("${kafka.producer.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION:5}")
    private String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION;

	@Value("${kafka.producer.BATCH_SIZE_CONFIG:64000}")
    private String PRODUCER_BATCH_SIZE_CONFIG;

	@Value("${kafka.producer.LINGER_MS_CONFIG:100}")
    private String PRODUCER_LINGER_MS_CONFIG;

	@Value("${kafka.producer.ACKS_CONFIG:all}")
    private String ACKS_CONFIG;

	@Value("${kafka.producer.COMPRESSION_TYPE_CONFIG:snappy}")
    private String COMPRESSION_TYPE_CONFIG;

	@Value("${kafka.producer.DELIVERY_TIMEOUT_MS_CONFIG:300000}")
    private String DELIVERY_TIMEOUT_MS_CONFIG;

	@Value("${kafka.producer.ENABLE_IDEMPOTENCE_CONFIG:true}")
    private String ENABLE_IDEMPOTENCE_CONFIG;

    private ProducerFactory<String, MsApiTopic> producerFactory() {
		Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        config.put(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG);
        config.put(ProducerConfig.LINGER_MS_CONFIG, PRODUCER_LINGER_MS_CONFIG);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, PRODUCER_BATCH_SIZE_CONFIG);
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, COMPRESSION_TYPE_CONFIG);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, ENABLE_IDEMPOTENCE_CONFIG);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, DELIVERY_TIMEOUT_MS_CONFIG);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name="error-kafka-template")
    public KafkaTemplate<String, MsApiTopic> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}