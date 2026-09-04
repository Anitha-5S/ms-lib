package com.c2.lc.lib;

import com.c2.lc.lib.utils.SystemHelper;

public class BaseTest {

    protected SystemHelper helper = new SystemHelper();

    protected int base = 100 ;

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }
}
