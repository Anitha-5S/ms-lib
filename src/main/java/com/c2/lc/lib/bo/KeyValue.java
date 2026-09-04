package com.c2.lc.lib.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue {

    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;
}
