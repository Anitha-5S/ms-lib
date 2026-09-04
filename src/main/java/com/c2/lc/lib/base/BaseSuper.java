package com.c2.lc.lib.base;

import com.c2.lc.lib.utils.DataParser;
import com.c2.lc.lib.utils.SystemHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseSuper {
    @JsonIgnore
    @Autowired public SystemHelper helper;

    @JsonIgnore
    @Autowired protected DataParser dataParser;
}
