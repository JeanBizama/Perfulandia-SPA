package com.perfulandiaSPA.perfulandia.serviceusuario.controller;

import org.springframework.ui.Model;
import com.perfulandiaSPA.perfulandia.serviceusuario.model.Usuario;
import com.perfulandiaSPA.perfulandia.serviceusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, @RequestParam String contrasena, Model model) {
        Usuario usuario = usuarioService.findByCorreo(correo);
        if (usuario.getContrasena().equals(contrasena)) {
            model.addAttribute("error", "Credenciales correctas (aun no hay pagina de inicio)");
            return "login";
        }
        else {
            model.addAttribute("error", "Credenciales invalidas");
            return "login";
        }
    }

    @GetMapping("/register")
    public String mostrarRegister(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setRol("CLIENTE");
        usuarioService.save(usuario);
        return "redirect:/auth/login";

    }

}
