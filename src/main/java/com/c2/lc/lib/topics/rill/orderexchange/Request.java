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
public class Request {

    @JsonProperty("Header")
    Header header;

    @JsonProperty("Body")
    Body body;

}
