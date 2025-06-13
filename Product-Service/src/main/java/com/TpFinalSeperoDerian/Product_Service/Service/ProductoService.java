package com.TpFinalSeperoDerian.Product_Service.Service;

import com.TpFinalSeperoDerian.Product_Service.Entity.Producto;
import com.TpFinalSeperoDerian.Product_Service.Repository.IProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository productoRepo;

    @Override
    public List<Producto> findAll() {
        return productoRepo.findAll();
    }

    @Override
    public Producto findByCodigoProducto(String codigoProducto) {
        return productoRepo.findById(codigoProducto)
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el producto con el codigo: " + codigoProducto));
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepo.save(producto);
    }

    @Override
    public Producto deleteProducto(String codigoProducto) {

        Producto producto = productoRepo.findById(codigoProducto)
                .orElseThrow(()-> new EntityNotFoundException("Producto no encontrado"));

        productoRepo.deleteById(codigoProducto);
        return producto;
    }

    @Override
    public Producto editProducto(String codigoProducto, Producto producto) {

        Optional<Producto> product = productoRepo.findById(codigoProducto);

        if (!product.isPresent()){
            throw new EntityNotFoundException("Producto no encontrado para su edicion");
        }
        if (producto.getNombreProducto() != null){
            product.get().setNombreProducto(producto.getNombreProducto());
        }
        if (producto.getMarcaProducto() != null){
            product.get().setMarcaProducto(producto.getMarcaProducto());
        }
        if (producto.getPrecioIndividualProducto() != null){
            product.get().setPrecioIndividualProducto(producto.getPrecioIndividualProducto());
        }

        return productoRepo.save(product.get());
    }
}
