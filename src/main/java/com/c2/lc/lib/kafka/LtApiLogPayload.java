package com.c2.lc.lib.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LtApiLogPayload {
    private String id;
    private String xDebugToken;
    private String status;
    private Object response;
    private Timestamp responseAt;
    private Timestamp requestedAt;
    private Timestamp producerStartedAt;
    private Timestamp producerEndedAt;
    private Long totalElapsedTime;
    private Long queueWaitTime;
    private Long appExecutionTime;
    private String topicName;
    private String groupId;
    private String partition;
    private String offset;
    private ApiRequestData requestData;
}