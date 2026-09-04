package com.c2.lc.lib.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ApiRequestData implements Serializable {

    @JsonProperty("c2_code")
    private String c2_code;

    @JsonProperty("br_code")
    private String br_code;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("uri")
    private String uri;

}
