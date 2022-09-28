package com.kakaopaysec.rrss.core.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject{

     VIEW("VIEW")
	,INCREASE("INCREASE")
	,REDUCE("REDUCE")
	,VOLUME("VOLUME");

    private final String key;
}