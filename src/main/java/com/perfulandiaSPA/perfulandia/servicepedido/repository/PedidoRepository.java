package com.perfulandiaSPA.perfulandia.servicepedido.repository;

import com.perfulandiaSPA.perfulandia.servicepedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
