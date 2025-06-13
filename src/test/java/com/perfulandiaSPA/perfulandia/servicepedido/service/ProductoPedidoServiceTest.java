package com.perfulandiaSPA.perfulandia.servicepedido.service;

import com.perfulandiaSPA.perfulandia.servicepedido.model.ProductoPedido;
import com.perfulandiaSPA.perfulandia.servicepedido.repository.ProductoPedidoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductoPedidoServiceTest {

    @Mock
    private ProductoPedidoRepository productoPedidoRepository;

    @InjectMocks
    private ProductoPedidoService productoPedidoService;

    ProductoPedido productoPedido = new ProductoPedido(1,3,5,10);

    public ProductoPedidoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        when(productoPedidoRepository.findAll()).thenReturn(
                Arrays.asList(productoPedido)
        );

        var result = productoPedidoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(productoPedidoRepository.findById(1L)).thenReturn(Optional.of(productoPedido));
        var result = productoPedidoService.findById(1L);
        assertEquals(10, result.getCantidad());
    }

    @Test
    public void saveTest() {
        when(productoPedidoRepository.save(productoPedido)).thenReturn(productoPedido);
        var result = productoPedidoService.save(productoPedido);
        assertEquals(5, result.getIdProducto());
    }

    @Test
    public void deleteTest() {
        productoPedidoService.delete(1L);
        verify(productoPedidoRepository, times(1)).deleteById(1L);
    }
}
