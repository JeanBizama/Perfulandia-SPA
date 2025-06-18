package com.perfulandiaSPA.perfulandia.serviceusuario.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandiaSPA.perfulandia.serviceusuario.controller.UsuarioControllerV2;
import com.perfulandiaSPA.perfulandia.serviceusuario.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerPorId(Long.valueOf(usuario.getId()))).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listar()).withRel("usuarios")

        );
    }
}
