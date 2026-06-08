package com.example.frontendservice.controller;

import com.example.frontendservice.dto.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Arrays;

@Controller
public class ProductoViewController {

    private final RestClient restClient = RestClient.create();

    @GetMapping("/productos")
    public String productos(Model model) {

        Producto[] productos = restClient.get()
                .uri("http://localhost:8082/productos")
                .retrieve()
                .body(Producto[].class);

        model.addAttribute("productos",
                Arrays.asList(productos));

        model.addAttribute("producto",
                new Producto());

        return "productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Producto producto = restClient.get()
                .uri("http://localhost:8082/productos/" + id)
                .retrieve()
                .body(Producto.class);

        Producto[] productos = restClient.get()
                .uri("http://localhost:8082/productos")
                .retrieve()
                .body(Producto[].class);

        model.addAttribute("producto", producto);
        model.addAttribute("productos",
                Arrays.asList(productos));

        return "productos";
    }

    @PostMapping("/productos/guardar")
    public String guardar(
            @ModelAttribute Producto producto) {

        if (producto.getId() == null) {

            restClient.post()
                    .uri("http://localhost:8082/productos")
                    .body(producto)
                    .retrieve()
                    .toBodilessEntity();

        } else {

            restClient.put()
                    .uri("http://localhost:8082/productos/" + producto.getId())
                    .body(producto)
                    .retrieve()
                    .toBodilessEntity();
        }

        return "redirect:/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        restClient.delete()
                .uri("http://localhost:8082/productos/" + id)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/productos";
    }
}