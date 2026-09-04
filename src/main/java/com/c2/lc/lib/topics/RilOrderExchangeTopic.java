package com.c2.lc.lib.topics;

import com.c2.lc.lib.topics.rill.orderexchange.RequestWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RilOrderExchangeTopic extends BaseTopic {
    private RequestWrapper topicData;
    private String c2Code;
    private String brCode;
    private String year;
    private String prefix;
    private String srNo;
}