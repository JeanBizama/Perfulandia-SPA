package com.perfulandiaSPA.perfulandia.servicelogistica.controller;

import com.perfulandiaSPA.perfulandia.servicelogistica.model.Envio;
import com.perfulandiaSPA.perfulandia.servicelogistica.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
@Tag(name="Envios", description = "Operaciones relacionadas con los envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping()
    @Operation(summary = "Obtener todos los envios", description = "Obtiene una lista de todos los envios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de envios obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "204", description = "No hay envios disponibles")
    })
    public ResponseEntity<List<Envio>> listar(){
        List<Envio> envios = envioService.findAll();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener envio por ID", description = "Retorna la información de un envio específico dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado")
    })
    public ResponseEntity<Envio> obtenerPorId(@PathVariable Long id) {
        Envio envio = envioService.findById(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un envio", description = "Actualiza un envio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "404", description = "Envio no encontrada")
    })
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @Valid @RequestBody Envio envio) {
        Envio existente = envioService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        envio.setId(id.intValue());
        return ResponseEntity.ok(envioService.save(envio));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo envio", description = "Crea un envio y retorna el envio creado con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envio creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos")
    })
    public ResponseEntity<Envio> crearEnvio(@Valid @RequestBody Envio envio) {
        Envio nuevo = envioService.save(envio);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un envio", description = "Elimina un envio existente dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Envio eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado")
    })
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        Envio existente = envioService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
