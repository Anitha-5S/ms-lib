package com.c2.lc.lib.api;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseVO implements Serializable {

	private static final long serialVersionUID = 8429176843379649572L;

	private String payload = null ;
	private int appStatusCode = -1;
	private List<String> messages = new ArrayList<>();
	private int unread = 0;

}
