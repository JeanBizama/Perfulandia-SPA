package com.perfulandiaSPA.perfulandia.servicepago.controller;

import com.perfulandiaSPA.perfulandia.servicepago.assembler.PagoModelAssembler;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/pagos")
@Tag(name="Pagos", description = "Operaciones relacionadas con los pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;


    @GetMapping()
    @Operation(summary = "Obtener todos los pagos", description = "Obtiene una lista de todos los pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "204", description = "No hay pagos disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> listar() {
        List<Pago> pagos = pagoService.findAll();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Pago>> pagosModel = pagos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(pagosModel,
                linkTo(methodOn(PagoControllerV2.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Retorna la información de un pago específico dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pago>> obtenerPorId(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        if (pago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pago));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago", description = "Actualiza un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pago>> actualizarPago(@PathVariable Long id, @Valid @RequestBody Pago pago) {
        Pago existente = pagoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pago.setId(id.intValue());
        Pago actualizado = pagoService.save(pago);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @Operation(summary = "Crear un nuevo pago", description = "Crea un pago y retorna el pago creado con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Pago>> crearPago(@Valid @RequestBody Pago pago) {
        Pago nuevo = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
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
