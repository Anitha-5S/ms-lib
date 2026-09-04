package com.c2.lc.lib.topics;

import com.c2.lc.lib.topics.eco.EcoApiLogPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EcoApiLogTopic extends BaseTopic {
    private EcoApiLogPayload topicData;
    private String logCollectionName;
}