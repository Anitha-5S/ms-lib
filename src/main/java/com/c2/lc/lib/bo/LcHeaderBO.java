package com.c2.lc.lib.bo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LcHeaderBO {

    private Long userId;

    private Long firmId;

    private String c2Code;
    private String brCode;

    private String terminalId;
    private String type;

}
