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
public class UsuarioServiceUnitTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioServiceUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(
                Arrays.asList(new Usuario(1, "Juan", "juan@mail.com", "1234", "CLIENTE"))
        );

        var result = usuarioService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario(1, "Ana", "ana@mail.com", "pass", "GERENTE");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var result = usuarioService.findById(1L);
        assertEquals("Ana", result.getNombre());
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario(null, "Luis", "luis@mail.com", "abc", "CLIENTE");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        var result = usuarioService.save(usuario);
        assertEquals("Luis", result.getNombre());
    }

    @Test
    public void testDelete() {
        usuarioService.delete(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
