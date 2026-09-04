package com.c2.lc.lib.topics.rill.orderexchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentModes {

    @JsonProperty("Amount")
    String Amount;

    @JsonProperty("PaymentModeId")
    String PaymentModeId;

    @JsonProperty("TXNRefNumber")
    String TXNRefNumber;

}
