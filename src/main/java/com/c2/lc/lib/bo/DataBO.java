package com.c2.lc.lib.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DataBO implements Serializable {

    private String data;
}
