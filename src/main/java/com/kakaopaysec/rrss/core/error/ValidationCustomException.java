package com.kakaopaysec.rrss.core.error;
import java.util.List;

import javax.validation.ValidationException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ====================================================================
 * Validation Custom Exception 클래스
 *
 * @packageName : com.tmoncorp.portal_api.core.error
 * @fileName : ValidationCustomException.java
 * @date : 2021-04-08
 * @version : 1.0
 * @author : 정민
 * @description : Validation Custom Exception 클래스
 * ====================================================================
 * DATE          AUTHOR      NOTE
 * --------------------------------------------------------------------
 * 2021-04-15    정민       최초생성
 * --------------------------------------------------------------------
 */
public class ValidationCustomException extends ValidationException {

    private static final long serialVersionUID = 1L;

    public ValidationCustomException() {
        super();
    }

    public ValidationCustomException(String message) {
        super(message);
    }

    public ValidationCustomException(List<String> messages) {
        super(convertListtoString(messages));
    }

    public ValidationCustomException(List<String> messages, Object obj) {
        super(convertListtoString(messages) + "@csv#" + ObjectToJsonString(obj));
    }

    public ValidationCustomException(Throwable cause) {
        super(cause);
    }

    public ValidationCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationCustomException(String message, List list) {
        super(message + "@csv#" + ObjectToJsonString(list));
    }

    private static String convertListtoString(List<String> messages) {
        String message = "";
        for (String msg : messages) {
            if (!("").equals(message)) {
                message += ";";
            }
            message += msg;

        }
        return message;
    }

    private static String ObjectToJsonString(Object param) {
        String paramJsonStr = "";
        try {
            ObjectMapper om = new ObjectMapper();
            paramJsonStr = om.writeValueAsString(param);
        }catch (Exception e) {
        }
        return paramJsonStr;
    }

}