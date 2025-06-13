package com.TpFinalSeperoDerian.Product_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

    private int status;
    private boolean exito;
    private String mensaje;
    private T data;
}

