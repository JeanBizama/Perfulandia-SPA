package com.perfulandiaSPA.perfulandia.serviceinventario.controller;

import com.perfulandiaSPA.perfulandia.serviceinventario.assembler.ProductoModelAssembler;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import com.perfulandiaSPA.perfulandia.serviceinventario.service.ProductoService;
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
@RequestMapping("/api/v2/productos")
@Tag(name="Productos", description = "Operaciones relacionadas con los productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping()
    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listar() {
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(productosModel,
                linkTo(methodOn(ProductoControllerV2.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna la información de un producto específico dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener producto por NOMBRE", description = "Retorna la información de un producto específico dado su NOMBRE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> obtenerPorNombre(@PathVariable String nombre) {
        Producto producto = productoService.findByNombre(nombre);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Producto existente = productoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        producto.setId(id.intValue());
        Producto actualizado = productoService.save(producto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto y retorna el producto creado con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos")
    })
    public ResponseEntity<EntityModel<Producto>> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto existente dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}