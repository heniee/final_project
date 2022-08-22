package com.example.demo.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.*;

@RestControllerAdvice
public class BoardControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> constraintViolatException() {
		   return ResponseEntity.status(HttpStatus.CONFLICT).body("데이터가 누락되었습니다");
		   }

}
