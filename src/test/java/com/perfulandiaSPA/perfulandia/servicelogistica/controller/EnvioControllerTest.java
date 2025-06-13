package com.perfulandiaSPA.perfulandia.servicelogistica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSPA.perfulandia.servicelogistica.model.Envio;
import com.perfulandiaSPA.perfulandia.servicelogistica.service.EnvioService;
import com.perfulandiaSPA.perfulandia.servicepago.model.Pago;
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
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Autowired
    private ObjectMapper objectMapper;

    Envio envio = new Envio(1,1, LocalDateTime.now(), "Los leones #23123");

    @Test
    public void getEnviosWithEnviosTest() throws Exception {
        Mockito.when(envioService.findAll()).thenReturn(List.of(envio));

        mockMvc.perform(get("/api/v1/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(1));
    }

    @Test
    public void getEnviosWithoutEnviosTest() throws Exception {
        Mockito.when(envioService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/envios"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void GetByIdTest() throws Exception {
        Mockito.when(envioService.findById(1L)).thenReturn(envio);

        mockMvc.perform(get("/api/v1/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccionDestino").value("Los leones #23123"));
    }

    @Test
    public void createEnvioTest() throws Exception {
        Mockito.when(envioService.save(Mockito.any(Envio.class))).thenReturn(envio);

        mockMvc.perform(post("/api/v1/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    public void updateEnvioTest() throws Exception {
        Mockito.when(envioService.findById(1L)).thenReturn(envio);
        Mockito.when(envioService.save(Mockito.any(Envio.class))).thenReturn(envio);

        mockMvc.perform(put("/api/v1/envios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    public void deleteEnvioTest() throws Exception {
        Mockito.doNothing().when(envioService).delete(1L);
        mockMvc.perform(delete("/api/v1/envios/1"))
                .andExpect(status().isNoContent());
    }
}
