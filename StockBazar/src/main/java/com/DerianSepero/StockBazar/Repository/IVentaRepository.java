package com.DerianSepero.StockBazar.Repository;

import com.DerianSepero.StockBazar.Model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta,Long> {
    List<Venta> findByFechaVenta(LocalDate fechaVenta);

    Venta findFirstByOrderByTotalDesc();
}
