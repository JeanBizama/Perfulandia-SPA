package com.perfulandiaSPA.perfulandia.servicepago.service;

import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import com.perfulandiaSPA.perfulandia.servicepago.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    Pago pago = new Pago(1, 101, 15000, "REALIZADO", LocalDateTime.now(), "EFECTIVO");

    public PagoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        when(pagoRepository.findAll()).thenReturn(
                Arrays.asList(pago)
        );

        var result = pagoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        var result = pagoService.findById(1L);
        assertEquals("EFECTIVO", result.getMetodo());
    }

    @Test
    public void saveTest() {
        when(pagoRepository.save(pago)).thenReturn(pago);
        var result = pagoService.save(pago);
        assertEquals(15000, result.getMonto());
    }

    @Test
    public void deleteTest() {
        pagoService.delete(1L);
        verify(pagoRepository, times(1)).deleteById(1L);
    }
}
