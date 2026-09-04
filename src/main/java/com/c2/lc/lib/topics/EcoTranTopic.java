package com.c2.lc.lib.topics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EcoTranTopic extends BaseTopic {
    private String topicData;
    private String c2Code;
    private String docNo;
    private String tranType;
}