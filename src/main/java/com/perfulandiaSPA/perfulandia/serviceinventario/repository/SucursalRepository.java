package com.perfulandiaSPA.perfulandia.serviceinventario.repository;

import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
}
