package com.perfulandiaSPA.perfulandia.servicepedido.service;


import com.perfulandiaSPA.perfulandia.servicepedido.model.Pedido;
import com.perfulandiaSPA.perfulandia.servicepedido.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    Pedido pedido = new Pedido(1,1,"RETIRADO",1000, LocalDateTime.now());

    public PedidoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        when(pedidoRepository.findAll()).thenReturn(
                Arrays.asList(pedido)
        );

        var result = pedidoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        var result = pedidoService.findById(1L);
        assertEquals("RETIRADO", result.getEstado());
    }

    @Test
    public void saveTest() {
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        var result = pedidoService.save(pedido);
        assertEquals(1000, result.getTotal());
    }

    @Test
    public void deleteTest() {
        pedidoService.delete(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }
}
