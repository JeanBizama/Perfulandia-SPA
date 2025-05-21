package com.perfulandiaSPA.perfulandia.servicelogistica.repository;
import com.perfulandiaSPA.perfulandia.servicelogistica.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long>{
}
