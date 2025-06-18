package com.perfulandiaSPA.perfulandia.servicepedido.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandiaSPA.perfulandia.servicepedido.controller.ProductoPedidoControllerV2;
import com.perfulandiaSPA.perfulandia.servicepedido.model.ProductoPedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoPedidoModelAssembler implements RepresentationModelAssembler<ProductoPedido, EntityModel<ProductoPedido>> {

    @Override
    public EntityModel<ProductoPedido> toModel(ProductoPedido productoPedido) {
        return EntityModel.of(productoPedido,
                linkTo(methodOn(ProductoPedidoControllerV2.class).obtenerPorId(Long.valueOf(productoPedido.getId()))).withSelfRel(),
                linkTo(methodOn(ProductoPedidoControllerV2.class).listar()).withRel("productos pedidos")

        );
    }
}