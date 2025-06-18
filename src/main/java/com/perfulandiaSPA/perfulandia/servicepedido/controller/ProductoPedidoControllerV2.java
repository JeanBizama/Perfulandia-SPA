package com.perfulandiaSPA.perfulandia.servicepedido.controller;

import com.perfulandiaSPA.perfulandia.servicepedido.assembler.ProductoPedidoModelAssembler;
import com.perfulandiaSPA.perfulandia.servicepedido.model.ProductoPedido;
import com.perfulandiaSPA.perfulandia.servicepedido.service.ProductoPedidoService;
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
@RequestMapping("/api/v2/productos-pedidos")
@Tag(name="Productos pedidos", description = "Operaciones relacionadas con los productos pedidos")
public class ProductoPedidoControllerV2 {

    @Autowired
    private ProductoPedidoService productoPedidoService;

    @Autowired
    private ProductoPedidoModelAssembler assembler;

    @GetMapping()
    @Operation(summary = "Obtener todos los productos pedidos", description = "Obtiene una lista de todos los productos pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos pedidos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoPedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay productos pedidos disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<ProductoPedido>>> listar() {
        List<ProductoPedido> productosPedidos = productoPedidoService.findAll();
        if (productosPedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<ProductoPedido>> productosPedidosModel = productosPedidos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(productosPedidosModel,
                linkTo(methodOn(ProductoPedidoControllerV2.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto pedido por ID", description = "Retorna la información de un producto pedido específico dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto pedido obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoPedido.class))),
            @ApiResponse(responseCode = "404", description = "Producto pedido no encontrado")
    })
    public ResponseEntity<EntityModel<ProductoPedido>> obtenerPorId(@PathVariable Long id) {
        ProductoPedido productoPedido = productoPedidoService.findById(id);
        if (productoPedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoPedido));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto pedido", description = "Actualiza un producto pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto pedido actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoPedido.class))),
            @ApiResponse(responseCode = "404", description = "Producto pedido no encontrado")
    })
    public  ResponseEntity<EntityModel<ProductoPedido>> actualizarProductoPedido(@PathVariable Long id, @Valid @RequestBody ProductoPedido productoPedido) {
        ProductoPedido existente = productoPedidoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        productoPedido.setId(id.intValue());
        ProductoPedido actualizado = productoPedidoService.save(productoPedido);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto pedido", description = "Crea un producto pedido y retorna el producto pedido creado con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto pedido creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoPedido.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos")
    })
    public ResponseEntity<EntityModel<ProductoPedido>> crearProductoPedido(@Valid @RequestBody ProductoPedido productoPedido) {
        ProductoPedido nuevo = productoPedidoService.save(productoPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto pedido", description = "Elimina un producto pedido existente dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto pedido no encontrado")
    })
    public ResponseEntity<Void> eliminarProductoPedido(@PathVariable Long id) {
        ProductoPedido productoPedido = productoPedidoService.findById(id);
        if (productoPedido == null) {
            return ResponseEntity.notFound().build();
        }
        productoPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}