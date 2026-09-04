package com.c2.lc.lib.utils;


import com.c2.lc.lib.exceptions.AppErrorException;
import com.c2.lc.lib.properties.Messages;

public class OffsetRange {

	private int offset = 0;
	private int size = Constants.MAX_RESULTS_LIST_COUNT;

	public OffsetRange() {
	}
	public OffsetRange(Integer offset, Integer size) throws AppErrorException {
		this.offset = (offset == null) ? this.offset : offset ;
		this.size = (size == null) ? this.size : size;
		if (this.size > Constants.MAX_RESULTS_LIST_COUNT) { throw new AppErrorException(size, Messages.TOO_MANY_RECORDS); }
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
