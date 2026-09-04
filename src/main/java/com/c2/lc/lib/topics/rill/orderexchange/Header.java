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
public class Header {

    @JsonProperty("DeviceId")
    String DeviceId;

    @JsonProperty("RequestTime")
    String RequestTime;

    @JsonProperty("Product")
    String Product;

    @JsonProperty("Version")
    String Version;

    @JsonProperty("RequestId")
    String RequestId;

}
