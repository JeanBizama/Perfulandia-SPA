package com.perfulandiaSPA.perfulandia.serviceinventario.service;

import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import com.perfulandiaSPA.perfulandia.serviceinventario.repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    Sucursal sucursal = new Sucursal(1, "Perfulandia Recoleta", "Recoleta #1234", "10:00");

    public SucursalServiceTest() { MockitoAnnotations.openMocks(this);}

    @Test
    public void findAllTest() {
        when(sucursalRepository.findAll()).thenReturn(
                Arrays.asList(sucursal)
        );

        var result = sucursalService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        var result = sucursalService.findById(1L);
        assertEquals("Perfulandia Recoleta", result.getNombre());
    }

    @Test
    public void saveTest() {
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        var result = sucursalService.save(sucursal);
        assertEquals("Recoleta #1234", result.getDireccion());
    }

    @Test
    public void deleteTest() {
        sucursalService.delete(1L);
        verify(sucursalRepository, times(1)).deleteById(1L);
    }
}
