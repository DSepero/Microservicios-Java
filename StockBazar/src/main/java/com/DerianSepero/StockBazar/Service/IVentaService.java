package com.DerianSepero.StockBazar.Service;

import com.DerianSepero.StockBazar.DTO.VentaCompletaDTO;
import com.DerianSepero.StockBazar.Model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    
    public void saveVenta(Venta venta);
    
    public List<Venta> getVentas();
    
    public Venta findVenta(Long codigo_venta);
    
    public void deleteVenta(Long codigo_venta);
    
    public void editVenta(Long codigo_venta, LocalDate nuevaFecha_venta,
                            Double nuevoTotal);

    public void editVenta(Venta venta);
       
    VentaCompletaDTO obtenerDatosMayorVenta();
}
