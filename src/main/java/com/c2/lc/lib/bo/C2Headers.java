package com.c2.lc.lib.bo;

import com.c2.lc.lib.utils.Constants;
import lombok.Data;

@Data
public class C2Headers {

    private String cC2Code = Constants.HYPHEN;
    private String cBrCode = Constants.HYPHEN;
    private Long nUserId = Constants.ROOT_USER;
    private Object info = new Object();

    public C2Headers(String cC2Code, String cBrCode, Long nUserId) {
        this.cC2Code = cC2Code;
        this.cBrCode = cBrCode;
        this.nUserId = nUserId;
    }

}
