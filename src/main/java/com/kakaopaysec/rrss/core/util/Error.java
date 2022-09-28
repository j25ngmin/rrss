package com.kakaopaysec.rrss.core.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private String field;
    private String defaultMessage;
    private Object rejectedValue;

    public Error() {
        super();
    }

    public Error(String field, String defaultMessage, Object rejectedValue) {
        super();
        this.field = field;
        this.defaultMessage = defaultMessage;
        this.rejectedValue = rejectedValue;
    }

}