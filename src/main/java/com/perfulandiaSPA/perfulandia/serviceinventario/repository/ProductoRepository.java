package com.perfulandiaSPA.perfulandia.serviceinventario.repository;

import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    @Query(value = "SELECT * FROM producto p WHERE p.nombre = :nombre", nativeQuery = true)
    Producto findByNombre(String nombre);

}
