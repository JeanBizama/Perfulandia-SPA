package com.perfulandiaSPA.perfulandia.serviceinventario.assembler;

import com.perfulandiaSPA.perfulandia.serviceinventario.controller.SucursalControllerV2;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).obtenerPorId(Long.valueOf(sucursal.getId()))).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).listar()).withRel("sucursales")
        );
    }
}