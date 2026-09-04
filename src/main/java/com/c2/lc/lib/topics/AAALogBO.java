package com.c2.lc.lib.topics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AAALogBO {


    @JsonProperty("timestamp")
    @SerializedName("timestamp")
    private String timestamp;


    @JsonProperty("sourceIp")
    @SerializedName("sourceIp")
    private String sourceIp;


    @JsonProperty("username")
    @SerializedName("username")
    private String username;


    @JsonProperty("SessionId")
    @SerializedName("SessionId")
    private String SessionId;


    @JsonProperty("task")
    @SerializedName("task")
    private String task;


    @JsonProperty("activityDone")
    @SerializedName("activityDone")
    private String activityDone;

}
