package com.DerianSepero.StockBazar.Controller;


import com.DerianSepero.StockBazar.DTO.VentaCompletaDTO;
import com.DerianSepero.StockBazar.Model.Venta;
import com.DerianSepero.StockBazar.Service.IVentaService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VentaController {
    
    @Autowired
    private IVentaService ventaServ;
    
    
    @PostMapping("/ventas/crear")
    ResponseEntity<Venta> crearVenta(@RequestBody Venta venta){
        
        ventaServ.saveVenta(venta);
        return ResponseEntity.ok(venta);
        
    }
    
    @GetMapping("/ventas")
    ResponseEntity<List<Venta>> getVentas(){
        List<Venta> listaVenta = ventaServ.getVentas();
        if (listaVenta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(listaVenta); 
    }
    
    
    @GetMapping("/ventas/{codigo_venta}")
    ResponseEntity<?> findVenta(@PathVariable Long codigo_venta){
        
        Venta venta = ventaServ.findVenta(codigo_venta);
        if (venta == null) {
            
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("La venta con el ID " + codigo_venta + " no fue encontrado");
        }
            return ResponseEntity.ok(venta);
    }
    
    //Obtener la lista de productos de una determinada venta
    @GetMapping("/ventas/productos/{codigo_venta}")
    public Venta findOnlyProductVentas (@PathVariable Long codigo_venta){
        
        Venta venta = ventaServ.findVenta(codigo_venta);
        
        if(venta.getCodigo_venta() == codigo_venta){
            
            return venta;            
        }else{
            return null;
        }
    }
    
    //Devuelve la venta con mayor total
    @GetMapping("/mayor_venta")
    public VentaCompletaDTO obtenerDatosMayorVenta() {
        return ventaServ.obtenerDatosMayorVenta();
    }
    
   
    @DeleteMapping("/venta/eliminar/{codigo_venta}")
    ResponseEntity<Void> deleteVenta(@PathVariable Long codigo_venta){
        
        Venta venta = ventaServ.findVenta(codigo_venta);
        if (venta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ventaServ.deleteVenta(codigo_venta);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PutMapping("/venta/editar/{codigo_venta}")
    ResponseEntity<?> editVenta(@PathVariable Long codigo_venta,
            @RequestParam(required=false, name="fecha_venta") LocalDate nuevaFecha_venta,
            @RequestParam(required=false, name="total") Double nuevoTotal){
        
        Venta venta = ventaServ.findVenta(codigo_venta);
        
        if(venta == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje","La venta con el ID " + codigo_venta + " no fue encontrado"));
        }
        ventaServ.editVenta(codigo_venta, nuevaFecha_venta, nuevoTotal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("mensaje","La venta con el ID " + codigo_venta + " fue editada correctamente.", "venta", venta));
    }
    
    @PutMapping("/personas/editar")
    public Venta editVenta(@RequestBody Venta venta) {

        ventaServ.editVenta(venta);
        return ventaServ.findVenta(venta.getCodigo_venta());
    }
    
    
    
    
    
    
}
