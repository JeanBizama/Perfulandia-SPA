package com.perfulandiaSPA.perfulandia.servicepedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="producto_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int idPedido;

    @Column(nullable = false)
    private int idProducto;

    @Column(nullable = false)
    private int cantidad;
}
