package com.perfulandiaSPA.perfulandia.serviceusuario.service;

import com.perfulandiaSPA.perfulandia.serviceusuario.model.Usuario;
import com.perfulandiaSPA.perfulandia.serviceusuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    Usuario usuario = new Usuario(1, "Juan", "juan@mail.com", "1234", "CLIENTE");

    @Test
    public void findAllTest() {
        when(usuarioRepository.findAll()).thenReturn(
                Arrays.asList(usuario)
        );

        var result = usuarioService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var result = usuarioService.findById(1L);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    public void saveTest() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        var result = usuarioService.save(usuario);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    public void deleteTest() {
        usuarioService.delete(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
