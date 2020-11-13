package com.test.spring.learn.retainall;

public class OrdersDO {
	long appId;
	long TradeAmount;
	int Status;
	/**
	 * @return the appId
	 */
	public long getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(long appId) {
		this.appId = appId;
	}
	/**
	 * @return the tradeAmount
	 */
	public long getTradeAmount() {
		return TradeAmount;
	}
	/**
	 * @param tradeAmount the tradeAmount to set
	 */
	public void setTradeAmount(long tradeAmount) {
		TradeAmount = tradeAmount;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return Status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		Status = status;
	}
	

}
