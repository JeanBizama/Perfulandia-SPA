package com.perfulandiaSPA.perfulandia.servicepago.assembler;

import com.perfulandiaSPA.perfulandia.servicepago.controller.PagoControllerV2;
import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).obtenerPorId(Long.valueOf(pago.getId()))).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).listar()).withRel("pagos")
        );
    }
}