package com.c2.lc.lib.topics.eco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcoApiLogPayload {
    private String id;
    private String status;
    private Object response;
    private String responseAt;
    private String requestedAt;
    private String topic;
    private String groupId;
    private String partition;
    private String offset;
    private EcoRequestPayload requestData;
}