package com.kakaopaysec.rrss.core.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopaysec.rrss.core.error.ValidationCustomException;
import com.kakaopaysec.rrss.core.util.ProcessResult;

import net.minidev.json.JSONObject;

@EnableWebMvc
@ControllerAdvice
public class ErrorExceptionHandler {

	@ExceptionHandler({ ServletRequestBindingException.class })
	public ResponseEntity<Object> ServletRequestBindingException(Exception ex, WebRequest request) {
		ProcessResult<Boolean> result = new ProcessResult<Boolean>();
		result.setCompleted(false);
		JSONObject jsonObject = new JSONObject();
		result.setMessage("필수 파라메터가 누락되었습니다.");
		jsonObject.put("message", "필수 파라메터가 누락되었습니다.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json; charset=utf-8");
		return new ResponseEntity<Object>(result, header, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> ConstraintViolationException(Exception ex, WebRequest request) {
		ProcessResult<Boolean> result = new ProcessResult<Boolean>();
		result.setCompleted(false);
		JSONObject jsonObject = new JSONObject();
		result.setMessage("필수 파라메터가 누락되었습니다.");
		jsonObject.put("message", "필수 파라메터가 누락되었습니다.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json; charset=utf-8");
		return new ResponseEntity<Object>(result, header, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ResourceNotFoundException.class, NotFoundException.class, NoSuchElementException.class,
			NoHandlerFoundException.class })
	public ResponseEntity<Object> ResourceNotFoundException(Exception ex, WebRequest request) {
		ProcessResult<Boolean> result = new ProcessResult<Boolean>();
		result.setCompleted(false);
		JSONObject jsonObject = new JSONObject();
		result.setMessage("요청한 리소스가 존재하지 않습니다.");
		jsonObject.put("message", "요청한 리소스가 존재하지 않습니다.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json; charset=utf-8");
		return new ResponseEntity<Object>(result, header, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ ValidationCustomException.class })
	public ResponseEntity<Object> validationCustomException(Exception ex) {
		ProcessResult<Boolean> result = new ProcessResult<Boolean>();
		result.setMessage(ex.getMessage());
		result.setCompleted(false);

		String exMessage = ex.getMessage();
		String message = "";
		String param = "";

		String[] exArr = exMessage.split("@csv#");
		if (exMessage != null && !("").equals(exMessage)) {
			message = exArr[0];
			if (exArr.length > 1) {
				param = exArr[1];
			}
		}

		if (message == null || ("").equals(message)) {
			message = "요청하신 호출을 실패 하였습니다.";
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", message);
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json; charset=utf-8");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request) {
		ProcessResult<Boolean> result = new ProcessResult<Boolean>();
		BindingResult bindingResult = ex.getBindingResult();
		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			// builder.append("{");
			// builder.append("error field: "+fieldError.getField());
			builder.append(fieldError.getDefaultMessage());
			// builder.append(" value: "+fieldError.getRejectedValue());
			// builder.append("}, ");
		}
		String errors = builder.toString();
		result.setMessage(errors);
		result.setCompleted(false);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", errors);
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json; charset=utf-8");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> InvalidPropertyException(Exception ex, WebRequest request) {
		ProcessResult<Boolean> result = new ProcessResult<Boolean>();
		result.setCompleted(false);
		JSONObject jsonObject = new JSONObject();
		result.setMessage("요청을 실패하였습니다.");
		jsonObject.put("message", "요청을 실패하였습니다.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json; charset=utf-8");
		return new ResponseEntity<Object>(result, header, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
