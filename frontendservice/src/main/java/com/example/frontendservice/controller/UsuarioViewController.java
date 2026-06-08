package com.example.frontendservice.controller;

import com.example.frontendservice.dto.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Controller
public class UsuarioViewController {

    private final RestClient restClient = RestClient.create();

    @GetMapping("/usuarios")
    public String usuarios(Model model) {

        Usuario[] usuarios = restClient.get()
                .uri("http://localhost:8081/usuarios")
                .retrieve()
                .body(Usuario[].class);

        model.addAttribute("usuarios", Arrays.asList(usuarios));
        model.addAttribute("usuario", new Usuario());

        return "usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Usuario usuario = restClient.get()
                .uri("http://localhost:8081/usuarios/" + id)
                .retrieve()
                .body(Usuario.class);

        Usuario[] usuarios = restClient.get()
                .uri("http://localhost:8081/usuarios")
                .retrieve()
                .body(Usuario[].class);

        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios", Arrays.asList(usuarios));

        return "usuarios";
    }

    @PostMapping("/usuarios/guardar")
    public String guardar(
            @ModelAttribute Usuario usuario) {

        if (usuario.getId() == null) {

            restClient.post()
                    .uri("http://localhost:8081/usuarios")
                    .body(usuario)
                    .retrieve()
                    .toBodilessEntity();

        } else {

            restClient.put()
                    .uri("http://localhost:8081/usuarios/" + usuario.getId())
                    .body(usuario)
                    .retrieve()
                    .toBodilessEntity();

        }

        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        restClient.delete()
                .uri("http://localhost:8081/usuarios/" + id)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/usuarios";
    }
}