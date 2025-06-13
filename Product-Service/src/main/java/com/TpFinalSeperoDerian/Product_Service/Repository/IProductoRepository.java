package com.TpFinalSeperoDerian.Product_Service.Repository;

import com.TpFinalSeperoDerian.Product_Service.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,String> {
}
