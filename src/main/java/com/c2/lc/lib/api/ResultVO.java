package com.c2.lc.lib.api;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResultVO implements Serializable {

	private static final long serialVersionUID = 3700528657223639428L;

	private Object payload = null ;
	private int appStatusCode = -1 ;
	private List<String> messages = new ArrayList<>();
	private String authToken=null;
	private int unread = 0;

}
