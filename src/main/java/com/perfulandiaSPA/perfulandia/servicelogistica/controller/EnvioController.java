package com.perfulandiaSPA.perfulandia.servicelogistica.controller;

import com.perfulandiaSPA.perfulandia.servicelogistica.model.Envio;
import com.perfulandiaSPA.perfulandia.servicelogistica.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping()
    public ResponseEntity<List<Envio>> listar(){
        List<Envio> envios = envioService.findAll();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerPorId(@PathVariable Long id) {
        Envio envio = envioService.findById(id);
        return ResponseEntity.ok(envio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        Envio existente = envioService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        envio.setId(id.intValue());
        return ResponseEntity.ok(envioService.save(envio));
    }

    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        Envio nuevo = envioService.save(envio);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
