package com.perfulandiaSPA.perfulandia.servicepedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name="pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int idCliente;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private LocalDateTime fechaPedido;
}
