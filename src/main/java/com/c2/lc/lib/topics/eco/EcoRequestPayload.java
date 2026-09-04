package com.c2.lc.lib.topics.eco;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcoRequestPayload {
    @JsonProperty("c2_code")
    private String c2_code;

    @JsonProperty("br_code")
    private String br_code;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("uri")
    private String uri;

}