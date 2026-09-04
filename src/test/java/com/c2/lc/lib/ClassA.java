package com.c2.lc.lib;

import java.time.LocalDateTime;

public class ClassA extends BaseTest {

    private int a;
    private LocalDateTime time;

    public ClassA() {
        this.time = helper.getCurrentTime();
        this.a = 10;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
