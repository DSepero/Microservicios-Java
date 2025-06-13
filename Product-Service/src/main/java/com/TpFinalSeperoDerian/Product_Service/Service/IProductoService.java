package com.TpFinalSeperoDerian.Product_Service.Service;

import com.TpFinalSeperoDerian.Product_Service.Entity.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> findAll();

    public Producto findByCodigoProducto (String codigoProducto);

    public Producto saveProducto (Producto producto);

    public Producto deleteProducto (String codigoProducto);

    public Producto editProducto (String codigoProducto, Producto producto);
}
