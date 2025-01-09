package com.DerianSepero.StockBazar.Service;

import com.DerianSepero.StockBazar.DTO.VentaCompletaDTO;
import com.DerianSepero.StockBazar.Model.Venta;
import com.DerianSepero.StockBazar.Repository.IVentaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepo;
    
      
    //Crear Venta
    @Override
    public void saveVenta(Venta venta) {
        ventaRepo.save(venta);
    }

    //Mostrar todas las ventas
    @Override
    public List<Venta> getVentas() {
        List<Venta> listaVentas = ventaRepo.findAll();
        return listaVentas;
    }

    //Mostrar venta por ID ( codigo_venta )
    @Override
    public Venta findVenta(Long codigo_venta) {
       Venta venta = ventaRepo.findById(codigo_venta).orElse(null);
       return venta;
    }

    //Eliminar venta
    @Override
    public void deleteVenta(Long codigo_venta) {
        ventaRepo.deleteById(codigo_venta);
    }

    //Editar venta
    @Override
    public void editVenta(Long codigo_venta, LocalDate nuevaFechaVenta, Double nuevoTotal) {
        
        Venta venta = this.findVenta(codigo_venta);
        venta.setFechaVenta(nuevaFechaVenta);
        venta.setTotal(nuevoTotal);
        this.saveVenta(venta);
    }

    //Editar venta (todos los campos obligatorios)
    @Override
    public void editVenta(Venta venta) {
        this.saveVenta(venta); 
    }
    
     // Mostrar la mayor venta
    @Override
    public VentaCompletaDTO obtenerDatosMayorVenta() {

        Venta ventaMayorMonto = ventaRepo.findFirstByOrderByTotalDesc();

        if (ventaMayorMonto != null) {
            VentaCompletaDTO ventaDTO = new VentaCompletaDTO();
            ventaDTO.setCodigo_venta(ventaMayorMonto.getCodigo_venta());
            ventaDTO.setTotal(ventaMayorMonto.getTotal());
            ventaDTO.setCantidad_disponible(ventaMayorMonto.getListaProductos().size());
            ventaDTO.setNombre(ventaMayorMonto.getUnCliente().getNombre());
            ventaDTO.setApellido(ventaMayorMonto.getUnCliente().getApellido());
            return ventaDTO;
        } else {
            return null;
        }
    }
    

}
