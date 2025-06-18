package com.perfulandiaSPA.perfulandia.servicelogistica.assembler;

import com.perfulandiaSPA.perfulandia.servicelogistica.controller.EnvioControllerV2;
import com.perfulandiaSPA.perfulandia.servicelogistica.model.Envio;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).obtenerPorId(Long.valueOf(envio.getId()))).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).listar()).withRel("envios")
        );
    }
}