package com.orderservice.OrderService.exception;

import com.orderservice.OrderService.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(customException.getErrorCode());
        errorResponse.setErrorMessage(customException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(customException.getStatus()));
    }


}
