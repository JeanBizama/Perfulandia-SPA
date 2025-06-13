package com.perfulandiaSPA.perfulandia.servicepago.controller;

import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import com.perfulandiaSPA.perfulandia.servicepago.service.PagoService;
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
@RequestMapping("/api/v1/pagos")
@Tag(name="Pagos", description = "Operaciones relacionadas con los pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping()
    @Operation(summary = "Obtener todos los pagos", description = "Obtiene una lista de todos los pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "204", description = "No hay pagos disponibles")
    })
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> pagos = pagoService.findAll();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Retorna la información de un pago específico dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Pago> obtenerPorID(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        if (pago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pago);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago", description = "Actualiza un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrada")
    })
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @Valid @RequestBody Pago pago) {
        Pago existente = pagoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pago.setId(id.intValue());
        return ResponseEntity.ok(pagoService.save(pago));
    }

    @Operation(summary = "Crear un nuevo pago", description = "Crea un pago y retorna el pago creado con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos")
    })
    @PostMapping
    public ResponseEntity<Pago> crearPago(@Valid @RequestBody Pago pago) {
        Pago nuevo = pagoService.save(pago);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago existente dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        Pago existente = pagoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
