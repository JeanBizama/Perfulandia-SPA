package com.perfulandiaSPA.perfulandia.servicepedido.controller;

import com.perfulandiaSPA.perfulandia.servicepedido.model.ProductoPedido;
import com.perfulandiaSPA.perfulandia.servicepedido.service.ProductoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos-pedidos")
public class ProductoPedidoController {

    @Autowired
    private ProductoPedidoService productoPedidoService;

    @GetMapping()
    public ResponseEntity<List<ProductoPedido>> listar() {
        List<ProductoPedido> productosPedidos = productoPedidoService.findAll();
        if (productosPedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosPedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoPedido> obtenerPorID(@PathVariable Long id) {
        ProductoPedido productoPedido = productoPedidoService.findById(id);
        return ResponseEntity.ok(productoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoPedido> actualizarProductoPedido(@PathVariable Long id, @RequestBody ProductoPedido productoPedido) {
        ProductoPedido existente = productoPedidoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        productoPedido.setId(id.intValue());
        return ResponseEntity.ok(productoPedidoService.save(productoPedido));
    }

    @PostMapping
    public ResponseEntity<ProductoPedido> crearProductoPedido(@RequestBody ProductoPedido productoPedido) {
        ProductoPedido nuevo = productoPedidoService.save(productoPedido);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProductoPedido(@PathVariable Long id) {
        productoPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
