package com.c2.lc.lib.topics;

import com.c2.lc.lib.topics.eco.EcoRequestPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EcoTopic extends BaseTopic{
    private EcoRequestPayload topicData;
}