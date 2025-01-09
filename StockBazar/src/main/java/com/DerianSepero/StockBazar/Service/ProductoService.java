package com.DerianSepero.StockBazar.Service;

import com.DerianSepero.StockBazar.Model.Producto;
import com.DerianSepero.StockBazar.Repository.IProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository productoRepo;
    
    //Crea un producto
    @Override
    public void saveProduct(Producto producto) {
               
        productoRepo.save(producto);        
    }

    //Muestra los productos
    @Override
    public List<Producto> getProducts() {
        
        List<Producto> listaProductos = productoRepo.findAll();
        return listaProductos;        
    }

    //Muestra un producto segun su ID
    @Override
    public Producto findProduct(Long codigo_producto) {
        Producto producto = productoRepo.findById(codigo_producto).orElse(null);
        return producto;
    }

    //Elimina un producto segun su ID
    @Override
    public void deleteProduct(Long codigo_producto) {
        productoRepo.deleteById(codigo_producto);
    }

    //Edita un producto
    @Override
    public void editProduct(Long codigo_producto, String nuevoNombre, String nuevaMarca, Double nuevoCosto, Double nuevaCantidadDisponible) {

        Producto producto = this.findProduct(codigo_producto);
        producto.setNombre(nuevoNombre);
        producto.setMarca(nuevaMarca);
        producto.setCosto(nuevoCosto);
        producto.setCantidadDisponible(nuevaCantidadDisponible);
        this.saveProduct(producto);
        
    }

    //Edita un producto (todos los campos obligatorios)
    @Override
    public void editProducto(Producto producto) {
        this.saveProduct(producto);
    }

    //Ver productos que tengan cantidad <= 5
    @Override
    public List<Producto> productosConFaltaStock() {
        return productoRepo.findByCantidadDisponibleLessThan(5.0);
    }
    
    
}
