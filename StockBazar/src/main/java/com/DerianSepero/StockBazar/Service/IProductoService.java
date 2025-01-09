package com.DerianSepero.StockBazar.Service;

import com.DerianSepero.StockBazar.Model.Producto;
import java.util.List;

public interface IProductoService {
    
    public void saveProduct(Producto producto);
    
    public List<Producto> getProducts();
    
    public Producto findProduct(Long codigo_producto);
    
    public void deleteProduct(Long codigo_producto);
    
    public void editProduct(Long codigo_producto, String nuevoNombre,
                            String nuevaMarca, Double nuevoCosto,
                            Double nuevaCantidad_disponible);

    public void editProducto(Producto producto);
    
    List<Producto> productosConFaltaStock();
    
}
