package com.perfulandiaSPA.perfulandia.servicepago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
import com.perfulandiaSPA.perfulandia.servicepago.service.PagoService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    Pago pago = new Pago(1, 101, 15000, "REALIZADO", LocalDateTime.now(), "EFECTIVO");

    @Test
    public void getPagosWithPagosTest() throws Exception {
        Mockito.when(pagoService.findAll()).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(101));
    }

    @Test
    public void getPagosWithoutPagosTest() throws Exception {
        Mockito.when(pagoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetByIdTest() throws Exception {
        Mockito.when(pagoService.findById(1L)).thenReturn(pago);

        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("REALIZADO"))
                .andExpect(jsonPath("$.metodo").value("EFECTIVO"));
    }

    @Test
    public void createPagoTest() throws Exception {
        Mockito.when(pagoService.save(Mockito.any(Pago.class))).thenReturn(pago);

        mockMvc.perform(post("/api/v1/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPedido").value(101));
    }

    @Test
    public void updatePagoTest() throws Exception {
        Mockito.when(pagoService.findById(1L)).thenReturn(pago);
        Mockito.when(pagoService.save(Mockito.any(Pago.class))).thenReturn(pago);

        mockMvc.perform(put("/api/v1/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(15000));
    }

    @Test
    public void deletePagoTest() throws Exception {
        Mockito.doNothing().when(pagoService).delete(1L);
        mockMvc.perform(delete("/api/v1/pagos/1"))
                .andExpect(status().isNoContent());
    }
}

