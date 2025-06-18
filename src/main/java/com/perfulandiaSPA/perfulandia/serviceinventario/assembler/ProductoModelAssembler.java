package com.perfulandiaSPA.perfulandia.serviceinventario.assembler;

import com.perfulandiaSPA.perfulandia.serviceinventario.controller.ProductoControllerV2;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).obtenerPorId(Long.valueOf(producto.getId()))).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).listar()).withRel("productos")
        );
    }
}

