package com.TpFinalSeperoDerian.Product_Service.Exceptions;

import com.TpFinalSeperoDerian.Product_Service.DTO.APIResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleEntityNotFoundException (EntityNotFoundException e){
        APIResponse<Object> response = new APIResponse<>(404, false,"La entidad solicitada no fue encontrada" + e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e){
        APIResponse<Object> response = new APIResponse<>(400,false, "El argumento ingresado no es valido" + e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleException(Exception e){
        APIResponse<Object> response = new APIResponse<>(500,false,"Error interno del servidor" + e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
