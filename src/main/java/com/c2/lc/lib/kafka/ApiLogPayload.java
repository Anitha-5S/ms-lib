package com.c2.lc.lib.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiLogPayload {
    private String id;
    private String status;
    private Object response;
    private String responseAt;
    private String requestedAt;
    private String topicName;
    private String groupId;
    private String partition;
    private String offset;
    private ApiRequestData requestData;
}