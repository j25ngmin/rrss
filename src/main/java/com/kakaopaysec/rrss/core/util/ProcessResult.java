package com.kakaopaysec.rrss.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@Component
public class ProcessResult<T> {

    private static MessageSource messageSource;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource initMessageSource;

    private boolean isCompleted;
    private String message;
    private T item;
    private String code;
    private List<Error> errors = new ArrayList<>();

    @PostConstruct
    private void initStaticMessage() {
        messageSource = this.initMessageSource;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCommonMessage(String key) {
        this.message = messageSource.getMessage(key, null, Locale.getDefault());
    }

    public void setCommonMessage(String key, String[] args) {
        this.message = messageSource.getMessage(key, args, Locale.getDefault());
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCommonCode(String key) {
        this.code = messageSource.getMessage(key, null, Locale.getDefault());
    }

    public void setCommonCode(String key, String[] args) {
        this.code = messageSource.getMessage(key, args, Locale.getDefault());
    }


    public List<Error> getErrors() {
        return errors;

    }
    public void setErrors(BindingResult bindingResult) {
        errors = new ArrayList<Error>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue()));
        }
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void setError(Error error) {
        this.errors.add(error);
    }

    /*
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JsonToStringStyle.JSON_STYLE);
    }
    */


}



