package com.perfulandiaSPA.perfulandia.servicepago.repository;

import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
