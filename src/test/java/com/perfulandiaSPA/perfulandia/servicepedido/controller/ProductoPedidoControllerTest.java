package com.perfulandiaSPA.perfulandia.servicepedido.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSPA.perfulandia.servicepedido.model.Pedido;
import com.perfulandiaSPA.perfulandia.servicepedido.model.ProductoPedido;
import com.perfulandiaSPA.perfulandia.servicepedido.service.ProductoPedidoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoPedidoService productoPedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    ProductoPedido productoPedido = new ProductoPedido(1,3,5,10);

    @Test
    public void getProductosPedidosWithProductosPedidosTest() throws Exception {
        Mockito.when(productoPedidoService.findAll()).thenReturn(List.of(productoPedido));

        mockMvc.perform(get("/api/v1/productos-pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(3));
    }


    @Test
    public void getProductosPedidosWithoutProductosPedidosTest() throws Exception {
        Mockito.when(productoPedidoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/productos-pedidos"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetByIdTest() throws Exception {
        Mockito.when(productoPedidoService.findById(1L)).thenReturn(productoPedido);

        mockMvc.perform(get("/api/v1/productos-pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(3))
                .andExpect(jsonPath("$.idProducto").value(5));
    }

    @Test
    public void createProductoPedidoTest() throws Exception {
        Mockito.when(productoPedidoService.save(Mockito.any(ProductoPedido.class))).thenReturn(productoPedido);

        mockMvc.perform(post("/api/v1/productos-pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoPedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cantidad").value(10));
    }

    @Test
    public void updateProductoPedidoTest() throws Exception {
        Mockito.when(productoPedidoService.findById(1L)).thenReturn(productoPedido);
        Mockito.when(productoPedidoService.save(Mockito.any(ProductoPedido.class))).thenReturn(productoPedido);

        mockMvc.perform(put("/api/v1/productos-pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoPedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(3));
    }

    @Test
    public void deleteProductoPedidoTest() throws Exception {
        Mockito.when(productoPedidoService.findById(1L)).thenReturn(productoPedido);
        Mockito.doNothing().when(productoPedidoService).delete(1L);
        mockMvc.perform(delete("/api/v1/productos-pedidos/1"))
                .andExpect(status().isNoContent());
    }

}
