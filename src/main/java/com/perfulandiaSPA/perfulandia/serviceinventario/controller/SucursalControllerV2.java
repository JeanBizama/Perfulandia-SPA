package com.perfulandiaSPA.perfulandia.serviceinventario.controller;

import com.perfulandiaSPA.perfulandia.serviceinventario.assembler.SucursalModelAssembler;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import com.perfulandiaSPA.perfulandia.serviceinventario.service.SucursalService;
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
@RequestMapping("/api/v2/sucursales")
@Tag(name="Sucursales", description = "Operaciones relacionadas con los sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping()
    @Operation(summary = "Obtener todas las sucursales", description = "Obtiene una lista de todas las sucursales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sucursal.class))),
            @ApiResponse(responseCode = "204", description = "No hay sucursales disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> listar() {
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Sucursal>> sucursalesModel = sucursales.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(sucursalesModel,
                linkTo(methodOn(SucursalControllerV2.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", description = "Retorna la información de una sucursal específica dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sucursal.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<Sucursal>> obtenerPorId(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza una sucursal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sucursal.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(@PathVariable Long id, @Valid @RequestBody Sucursal sucursal) {
        Sucursal existente = sucursalService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        sucursal.setId(id.intValue());
        Sucursal actualizado = sucursalService.save(sucursal);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sucursal", description = "Crea una sucursal y retorna la sucursal creada con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sucursal.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos")
    })
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(@Valid @RequestBody Sucursal sucursal) {
        Sucursal nuevo = sucursalService.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal existente dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
