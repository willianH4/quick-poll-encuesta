package com.williamhdz.quickpoll.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.williamhdz.quickpoll.dto.error.ErrorDetail;
import com.williamhdz.quickpoll.dto.error.ValidationError;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {
	
	@Inject
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDetail handleValidationError(MethodArgumentNotValidException manve, HttpServletRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		// Populate errorDetail instance
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String requestPath = (String) request.getAttribute("jakarta.servlet.error.request_uri");
		if (requestPath == null) {
			requestPath = request.getRequestURI();
		}
		errorDetail.setTitle("Validation Failed");
		errorDetail.setDetail("Input validation failed");
		errorDetail.setDeveloperMessage(manve.getClass().getName());
		
		// Create ValidationError instances
		List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			
			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fieldError.getField());
			if(validationErrorList == null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fieldError.getField(), validationErrorList);
			}
			
			ValidationError validationError = new ValidationError();
			validationError.setCode(fieldError.getCode());
			validationError.setMessage(messageSource.getMessage(fieldError, null));
			validationErrorList.add(validationError);
		}
		
		return errorDetail;
		
	}
	
}
