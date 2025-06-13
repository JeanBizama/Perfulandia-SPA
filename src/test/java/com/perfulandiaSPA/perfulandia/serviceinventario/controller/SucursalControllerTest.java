package com.perfulandiaSPA.perfulandia.serviceinventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Producto;
import com.perfulandiaSPA.perfulandia.serviceinventario.model.Sucursal;
import com.perfulandiaSPA.perfulandia.serviceinventario.service.SucursalService;
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
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    Sucursal sucursal = new Sucursal(1, "Perfulandia Recoleta", "Recoleta #1234", "10:00");

    @Test
    public void getSucursalesWithSucursalesTest() throws Exception {
        Mockito.when(sucursalService.findAll()).thenReturn(List.of(sucursal));

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Perfulandia Recoleta"));
    }

    @Test
    public void getSucursalesWithoutSucursalesTest() throws Exception {
        Mockito.when(sucursalService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetByIdTest() throws Exception {
        Mockito.when(sucursalService.findById(1L)).thenReturn(sucursal);

        mockMvc.perform(get("/api/v1/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfulandia Recoleta"))
                .andExpect(jsonPath("$.direccion").value("Recoleta #1234"));
    }

    @Test
    public void createSucursalTest() throws Exception {
        Mockito.when(sucursalService.save(Mockito.any(Sucursal.class))).thenReturn(sucursal);

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.horarioApertura").value("10:00"));
    }

    @Test
    public void updateSucursalTest() throws Exception {
        Mockito.when(sucursalService.findById(1L)).thenReturn(sucursal);
        Mockito.when(sucursalService.save(Mockito.any(Sucursal.class))).thenReturn(sucursal);

        mockMvc.perform(put("/api/v1/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("Recoleta #1234"));
    }

    @Test
    public void deleteSucursalTest() throws Exception {
        Mockito.when(sucursalService.findById(1L)).thenReturn(sucursal);
        Mockito.doNothing().when(sucursalService).delete(1L);
        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());
    }
}
