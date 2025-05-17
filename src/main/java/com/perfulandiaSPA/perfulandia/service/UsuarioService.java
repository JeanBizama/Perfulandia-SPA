package com.perfulandiaSPA.perfulandia.service;

import com.perfulandiaSPA.perfulandia.model.Usuario;
import com.perfulandiaSPA.perfulandia.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUser(Usuario sales){
        Usuario newUser = usuarioRepository.save(sales);
        return newUser;
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }



}
