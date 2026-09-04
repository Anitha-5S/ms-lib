package com.c2.lc.lib.topics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsApiTopic {
    private String id;
    private String uri;
    private Object payload;
    private LocalDateTime dateTime;
    private Map<String,String> headers;
}