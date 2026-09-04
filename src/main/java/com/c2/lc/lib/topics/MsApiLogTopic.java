package com.c2.lc.lib.topics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsApiLogTopic{
    private String id;
    private Long offset;
    private String status;
    private String groupId;
    private Object payload;
    private Object response;
    private String topicName;
    private Integer partition;
    private Long queueWaitTime;
    private Long totalElapsedTime;
    private Long appExecutionTime;
    private String collectionName;
    private LocalDateTime dateTime;
    private LocalDateTime pEndedAt;
    private LocalDateTime cEndedAt;
    private LocalDateTime pStartedAt;
    private LocalDateTime cStartedAt;
    private Map<String,String> headers;
    private String payloadContentType = MediaType.APPLICATION_JSON_VALUE;
    private String responseContentType = MediaType.APPLICATION_JSON_VALUE;
}
