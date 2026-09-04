package com.c2.lc.lib.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class LtApiRequestTopic extends BaseTopic {

    private ApiRequestData topicData;
    private String xDebugToken;
    private Timestamp producerStartAt;

}
