package com.c2.lc.lib.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ApiLogTopic extends BaseTopic {
    private ApiLogPayload topicData;
    private String logCollectionName;
}