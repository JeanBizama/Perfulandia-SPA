package com.perfulandiaSPA.perfulandia.servicelogistica.service;

import com.perfulandiaSPA.perfulandia.servicelogistica.model.Envio;
import com.perfulandiaSPA.perfulandia.servicelogistica.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    Envio envio = new Envio(1,1, LocalDateTime.now(), "Los leones #23123");

    public EnvioServiceTest() {MockitoAnnotations.openMocks(this); }

    @Test
    public void findAllTest() {
        when(envioRepository.findAll()).thenReturn(
                Arrays.asList(envio)
        );

        var result = envioService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        var result = envioService.findById(1L);
        assertEquals("Los leones #23123", result.getDireccionDestino());
    }

    @Test
    public void saveTest() {
        when(envioRepository.save(envio)).thenReturn(envio);
        var result = envioService.save(envio);
        assertEquals("Los leones #23123", result.getDireccionDestino());
    }

    @Test
    public void deleteTest() {
        envioService.delete(1L);
        verify(envioRepository, times(1)).deleteById(1L);
    }

}
