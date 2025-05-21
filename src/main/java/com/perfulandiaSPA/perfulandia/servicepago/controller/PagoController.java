package com.perfulandiaSPA.perfulandia.servicepago.controller;

import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import com.perfulandiaSPA.perfulandia.servicepago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping()
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> pagos = pagoService.findAll();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPorID(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        return ResponseEntity.ok(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        Pago existente = pagoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pago.setId(id.intValue());
        return ResponseEntity.ok(pagoService.save(pago));
    }

    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        Pago nuevo = pagoService.save(pago);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
