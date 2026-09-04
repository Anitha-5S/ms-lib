package com.c2.lc.lib.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NextPageBO {

    @SerializedName("n_next_page")
    private int page;

    @SerializedName("n_total")
    private int total;

}
