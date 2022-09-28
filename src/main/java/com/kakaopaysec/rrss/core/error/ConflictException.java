package com.kakaopaysec.rrss.core.error;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;


public class ConflictException extends HttpClientErrorException {


   public ConflictException(HttpStatus statusCode) {
      super(statusCode);
   }

   public ConflictException(HttpStatus statusCode, String statusText) {
      super(statusCode, statusText);
   }

   public ConflictException(HttpStatus statusCode, String statusText, byte[] body, Charset responseCharset) {
      super(statusCode, statusText, body, responseCharset);
   }

   public ConflictException(HttpStatus statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
      super(statusCode, statusText, headers, body, responseCharset);
   }

   public ConflictException(String message, HttpStatus statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
      super(message, statusCode, statusText, headers, body, responseCharset);
   }

}


