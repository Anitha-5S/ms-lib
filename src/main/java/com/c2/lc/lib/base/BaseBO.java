package com.c2.lc.lib.base;

import com.google.gson.annotations.Expose;

public class BaseBO {

    @Expose(serialize = false, deserialize = false)
    protected Object object;
}
