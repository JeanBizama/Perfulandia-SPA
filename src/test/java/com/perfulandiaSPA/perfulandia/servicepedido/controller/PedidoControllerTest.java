package com.perfulandiaSPA.perfulandia.servicepedido.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import com.perfulandiaSPA.perfulandia.servicepedido.model.Pedido;
import com.perfulandiaSPA.perfulandia.servicepedido.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    Pedido pedido = new Pedido(1,1,"RETIRADO",1000, LocalDateTime.now());

    @Test
    public void getPedidosWithPedidossTest() throws Exception {
        Mockito.when(pedidoService.findAll()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCliente").value(1));
    }

    @Test
    public void getPedidosWithoutPedidosTest() throws Exception {
        Mockito.when(pedidoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetByIdTest() throws Exception {
        Mockito.when(pedidoService.findById(1L)).thenReturn(pedido);

        mockMvc.perform(get("/api/v1/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.estado").value("RETIRADO"));
    }

    @Test
    public void createPedidoTest() throws Exception {
        Mockito.when(pedidoService.save(Mockito.any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCliente").value(1));
    }

    @Test
    public void updatePedidoTest() throws Exception {
        Mockito.when(pedidoService.findById(1L)).thenReturn(pedido);
        Mockito.when(pedidoService.save(Mockito.any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(put("/api/v1/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1000));
    }

    @Test
    public void deletePedidoTest() throws Exception {
        Mockito.when(pedidoService.findById(1L)).thenReturn(pedido);
        Mockito.doNothing().when(pedidoService).delete(1L);
        mockMvc.perform(delete("/api/v1/pedidos/1"))
                .andExpect(status().isNoContent());
    }



}
