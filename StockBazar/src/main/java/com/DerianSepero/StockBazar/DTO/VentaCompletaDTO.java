package com.DerianSepero.StockBazar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VentaCompletaDTO {
    
    private Long codigo_venta;
    private Double total;
    private Integer cantidad_disponible;
    private String nombre;
    private String apellido;

    public VentaCompletaDTO() {
    }

    public VentaCompletaDTO(Long codigo_venta, Double total, Integer cantidad_disponible, String nombre, String apellido) {
        this.codigo_venta = codigo_venta;
        this.total = total;
        this.cantidad_disponible = cantidad_disponible;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    
    
}
