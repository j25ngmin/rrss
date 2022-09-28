package com.kakaopaysec.rrss.core.util;

import lombok.Getter;

@Getter
public enum TradeType {
	
	TRADETYPE_001(1, "001"),
	TRADETYPE_002(2, "002");
	
	private int code;
	private String tradeType;
	
	TradeType(int code, String tradeType) {
		this.code = code;
		this.tradeType = tradeType;
	}

	public static TradeType findByCodeAndTradeType(int code) {
		TradeType result = null;
		for(TradeType item : TradeType.values()) {
			if(item.getCode() == code) {
				result = item;
				break;
			}
		}
		return result;
	}
	
	
}
