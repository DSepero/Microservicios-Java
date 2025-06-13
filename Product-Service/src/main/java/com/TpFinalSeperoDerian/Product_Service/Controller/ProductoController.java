package com.TpFinalSeperoDerian.Product_Service.Controller;

import com.TpFinalSeperoDerian.Product_Service.DTO.APIResponse;
import com.TpFinalSeperoDerian.Product_Service.Entity.Producto;
import com.TpFinalSeperoDerian.Product_Service.Service.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService productoServ;

    @GetMapping("/producto/ver")
    ResponseEntity<APIResponse<List<Producto>>> findAll(){
        List<Producto> listProducts = productoServ.findAll();
        if (listProducts.isEmpty()){
            throw new EntityNotFoundException("No hay productos registrados en la base de datos");
        }
        APIResponse<List<Producto>> response = new APIResponse<>(200,true,"Lista de productos obtenida con exito", listProducts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/ver/{codigoProducto}")
    ResponseEntity<APIResponse<Producto>> findByCodigoProduct(@PathVariable String codigoProducto){
        Producto producto = productoServ.findByCodigoProducto(codigoProducto);
        APIResponse<Producto> response = new APIResponse<>(200,true,"Producto encontrado con exito",producto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/producto/crear")
    ResponseEntity<APIResponse<Producto>> saveProduct (@RequestBody Producto producto){
        Producto product = productoServ.saveProducto(producto);
        APIResponse<Producto> response = new APIResponse<>(200, true, "Entidad producto creado correctamente en la BD", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/producto/borrar/{codigoProducto}")
    ResponseEntity<APIResponse<Producto>> deleteProduct (@PathVariable String codigoProducto){
        Producto product = productoServ.deleteProducto(codigoProducto);
        APIResponse<Producto> response = new APIResponse<>(200, true ,"Entidad producto eliminado correctamente", product);
        return ResponseEntity.ok(response);
        }

    @PatchMapping("/producto/editar/{codigoProducto}")
    ResponseEntity<APIResponse<Producto>> editProduct (@PathVariable String codigoProducto, @RequestBody Producto producto){

        Producto product = productoServ.editProducto(codigoProducto,producto);
        if (product == null){
            return ResponseEntity.badRequest().build();
        }
        APIResponse<Producto> response = new APIResponse<>(200,true,"Datos de la entidad producto editada correctamente", product);
        return ResponseEntity.ok(response);

    }
    }
