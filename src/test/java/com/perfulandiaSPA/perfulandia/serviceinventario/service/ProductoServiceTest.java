package com.perfulandiaSPA.perfulandia.serviceinventario.service;


import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import com.perfulandiaSPA.perfulandia.serviceinventario.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    Producto producto = new Producto(1,"perfume", "perfume olor vainilla", new BigDecimal("1000.0"), 5);

    public ProductoServiceTest() {MockitoAnnotations.openMocks(this);}

    @Test
    public void findAllTest() {
        when(productoRepository.findAll()).thenReturn(
                Arrays.asList(producto)
        );

        var result = productoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        var result = productoService.findById(1L);
        assertEquals("perfume", result.getNombre());
    }

    @Test
    public void saveTest() {
        when(productoRepository.save(producto)).thenReturn(producto);
        var result = productoService.save(producto);
        assertEquals("perfume olor vainilla", result.getDescripcion());
    }

    @Test
    public void deleteTest() {
        productoService.delete(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
