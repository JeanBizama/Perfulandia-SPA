package com.perfulandiaSPA.perfulandia.serviceusuario.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.perfulandiaSPA.perfulandia.serviceusuario.model.Usuario;
import com.perfulandiaSPA.perfulandia.serviceusuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import java.util.Arrays;


@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    Usuario usuario = new Usuario(1, "Juan", "juan@gmail.com", "password123","CLIENTE");

    @Test
    public void getUsersWithUsersTest() throws Exception {
        Mockito.when(usuarioService.findAll()).thenReturn(Arrays.asList(usuario));
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsersWithoutUsersTest() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    public void getUserByIdTest() throws Exception {
        Mockito.when(usuarioService.findById(1L)).thenReturn(usuario);
        mockMvc.perform(get("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByEmailTest() throws Exception {
        Mockito.when(usuarioService.findByCorreo("juan@gmail.com")).thenReturn(usuario);
        mockMvc.perform(get("/api/v1/usuarios/correo/juan@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void createUserTest() throws Exception {
        Mockito.when(usuarioService.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.correo").value("juan@gmail.com"));
    }


    @Test
    public void updateUserTest() throws Exception {
        Mockito.when(usuarioService.findById(1L)).thenReturn(usuario);
        Mockito.when(usuarioService.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserTest() throws Exception {
        Mockito.when(usuarioService.findById(1L)).thenReturn(usuario);
        Mockito.doNothing().when(usuarioService).delete(1L);
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
