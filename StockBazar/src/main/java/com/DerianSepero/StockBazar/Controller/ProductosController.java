package com.DerianSepero.StockBazar.Controller;

import com.DerianSepero.StockBazar.Model.Producto;
import com.DerianSepero.StockBazar.Service.IProductoService;
import java.util.ArrayList;
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
public class ProductosController {
    
    @Autowired
    private IProductoService productoServ;
    
    @PostMapping("/productos/crear")
    ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        
        productoServ.saveProduct(producto);        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @GetMapping("/productos")
    ResponseEntity<List<Producto>> listaProductos(){
        List<Producto> listaProductos = productoServ.getProducts();
        if (listaProductos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();      
        }
            return ResponseEntity.ok(listaProductos);
    }
    
    @GetMapping("/productos/{codigo_producto}")
    ResponseEntity<?> findProducto(@PathVariable Long codigo_producto){
        Producto producto = productoServ.findProduct(codigo_producto);
        
        if (producto == null) {
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto con ID " + codigo_producto + " no encontrado");
        }
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Producto con ID " + codigo_producto + " encontrado");
    }
    
    @DeleteMapping("/productos/eliminar/{codigo_producto}")
    ResponseEntity<Void> deleteProducto (@PathVariable Long codigo_producto){
        
        Producto producto = productoServ.findProduct(codigo_producto);
        
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
            productoServ.deleteProduct(codigo_producto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PutMapping("/productos/editar/{codigo_producto}")
    ResponseEntity<?> editProducto(@PathVariable Long codigo_producto,
            @RequestParam(required = false, name = "nombre") String nuevoNombre,
            @RequestParam(required = false, name = "marca") String nuevaMarca,
            @RequestParam(required = false, name = "costo") Double nuevoCosto,
            @RequestParam(required = false, name = "cantidad_disponible") Double nuevaCantidad_disponible){
        
        Producto producto = productoServ.findProduct(codigo_producto);
        
        if(producto == null){
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje","El producto con el ID " + codigo_producto + " no fue encontrado"));
        }   
          
        productoServ.editProduct(codigo_producto, nuevoNombre, nuevaMarca, nuevoCosto, nuevaCantidad_disponible);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of(
                "mensaje", "El producto con ID " + codigo_producto + " fue editada correctamente.",
                "producto", producto
            ));
        
    }
    
    @PutMapping("/productos/editar")
    public Producto editProducto(@RequestBody Producto producto) {

        productoServ.editProducto(producto);
        return productoServ.findProduct(producto.getCodigo_producto());
        
    }
    
       
    //Muestra la cantidad de productos que tengan menos de 5 unidades
    @GetMapping("/productos/falta_stock")
    public List<Producto> cantidadDisponible() {
        
        List<Producto> listaProductos = productoServ.getProducts();
        List<Producto> productosFaltaStock = new ArrayList<>();
        
        for (Producto p : listaProductos) {
            if (p.getCantidadDisponible() <= 5.0) {
                productosFaltaStock.add(p);
            }
        }
        return productosFaltaStock;
    }
        
}
    
    

