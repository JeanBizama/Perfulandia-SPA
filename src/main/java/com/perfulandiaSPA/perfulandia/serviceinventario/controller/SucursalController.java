package com.perfulandiaSPA.perfulandia.serviceinventario.controller;

import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import com.perfulandiaSPA.perfulandia.serviceinventario.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping()
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerPorID(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        return ResponseEntity.ok(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal existente = sucursalService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        sucursal.setId(id.intValue());
        return ResponseEntity.ok(sucursalService.save(sucursal));
    }

    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevo = sucursalService.save(sucursal);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
