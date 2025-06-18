package com.perfulandiaSPA.perfulandia.servicepedido.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandiaSPA.perfulandia.servicepedido.controller.PedidoControllerV2;
import com.perfulandiaSPA.perfulandia.servicepedido.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).obtenerPorId(Long.valueOf(pedido.getId()))).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).listar()).withRel("pedidos")

        );
    }
}
