package com.jsxztshaohaibo.vo;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>@Author: haohaibo</p>
 * <p>@date: 2018年4月14日 上午12:15:16</p>
 */
public class ResultVO {
	private String msg;
	private String type;
	private long timestamp;
	@Override
	public String toString() {
		return "ResultVO [msg=" + msg + ", type=" + type + ", timestamp="
				+ timestamp + "]";
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
