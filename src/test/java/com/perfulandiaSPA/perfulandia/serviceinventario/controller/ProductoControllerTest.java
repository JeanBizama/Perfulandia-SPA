package com.perfulandiaSPA.perfulandia.serviceinventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import com.perfulandiaSPA.perfulandia.serviceinventario.service.ProductoService;
import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    Producto producto = new Producto(1,"perfume", "perfume olor vainilla", new BigDecimal("1000.0"), 5);

    @Test
    public void getProductosWithProductosTest() throws Exception {
        Mockito.when(productoService.findAll()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stock").value(5));
    }

    @Test
    public void getProductosWithoutProductosTest() throws Exception {
        Mockito.when(productoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetByIdTest() throws Exception {
        Mockito.when(productoService.findById(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("perfume"))
                .andExpect(jsonPath("$.descripcion").value("perfume olor vainilla"));
    }

    @Test
    public void createProductoTest() throws Exception {
        Mockito.when(productoService.save(Mockito.any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.precio").value(1000.0));
    }

    @Test
    public void updateProductoTest() throws Exception {
        Mockito.when(productoService.findById(1L)).thenReturn(producto);
        Mockito.when(productoService.save(Mockito.any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/api/v1/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("perfume olor vainilla"));
    }

    @Test
    public void deleteProductoTest() throws Exception {
        Mockito.doNothing().when(productoService).delete(1L);
        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());
    }
}
