package com.nanonano.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

   @ExceptionHandler(ErrorHandler.class)
   public final ResponseEntity<Object> handleErrorHandler(ErrorHandler ex, WebRequest request) {
      Map<String, Object> body = new HashMap<>();
      body.put("timestamp", System.currentTimeMillis());
      body.put("status", ex.getStatus());
      body.put("message", ex.getMessage());
      body.put("details", request.getDescription(false));

      return new ResponseEntity<>(body, HttpStatus.valueOf(ex.getStatus()));
   }
}
