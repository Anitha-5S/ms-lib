package com.c2.lc.lib.bo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBO {

    @Min(value = 0, message = "n_page should not be less than 0")
    @SerializedName("n_page")
    private int page;

    @Min(value = 1, message = "n_limit should not be less than 1")
    @SerializedName("n_limit")
    private int limit;

}
