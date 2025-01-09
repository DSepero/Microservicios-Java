package com.DerianSepero.StockBazar.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long codigo_producto;
    private String nombre;
    private String marca;
    private Double costo;
    //@Column(name = "cantidad_disponible")
    private Double cantidadDisponible;

    @ManyToOne
    @JoinColumn(name="codigo_venta")
    @JsonBackReference
    private Venta venta;
    
    public Producto() {
    }

    public Producto(Long codigo_producto, String nombre, String marca, Double costo, Double cantidadDisponible, Venta venta) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidadDisponible = cantidadDisponible;
        this.venta = venta;
    }

    
}
