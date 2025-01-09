package com.DerianSepero.StockBazar.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Venta {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long codigo_venta;
    private LocalDate fechaVenta;
    private Double total;
    
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Producto> listaProductos;
    
    @OneToOne
    @JoinColumn(name="id_cliente", referencedColumnName = "id_cliente")
    private Cliente unCliente;

    public Venta() {
    }

    public Venta(Long codigo_venta, LocalDate fechaVenta, Double total, List<Producto> listaProductos, Cliente unCliente) {
        this.codigo_venta = codigo_venta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.listaProductos = listaProductos;
        this.unCliente = unCliente;
    }

    
    
}
