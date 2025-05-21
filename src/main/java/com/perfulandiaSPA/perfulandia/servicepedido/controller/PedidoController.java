package com.perfulandiaSPA.perfulandia.servicepedido.controller;

import com.perfulandiaSPA.perfulandia.servicepedido.model.Pedido;
import com.perfulandiaSPA.perfulandia.servicepedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping()
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorID(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido existente = pedidoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pedido.setId(id.intValue());
        return ResponseEntity.ok(pedidoService.save(pedido));
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevo = pedidoService.save(pedido);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
