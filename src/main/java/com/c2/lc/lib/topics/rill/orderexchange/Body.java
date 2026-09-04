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
public class Body {

    @JsonProperty("BillDetail")
    BillDetail billDetail;

    @JsonProperty("Latitude")
    String Latitude;

    @JsonProperty("Longitude")
    String Longitude;

    @JsonProperty("StoreId")
    String StoreId;

    @JsonProperty("POSTerminalId")
    String POSTerminalId;

}
