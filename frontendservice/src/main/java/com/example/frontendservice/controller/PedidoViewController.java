package com.example.frontendservice.controller;

import com.example.frontendservice.dto.Pedido;
import com.example.frontendservice.dto.Producto;
import com.example.frontendservice.dto.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Arrays;

@Controller
public class PedidoViewController {

    private final RestClient restClient = RestClient.create();

    @GetMapping("/pedidos")
    public String pedidos(Model model) {

        Pedido[] pedidos = restClient.get()
                .uri("http://localhost:8083/pedidos")
                .retrieve()
                .body(Pedido[].class);

        Usuario[] usuarios = restClient.get()
                .uri("http://localhost:8081/usuarios")
                .retrieve()
                .body(Usuario[].class);

        Producto[] productos = restClient.get()
                .uri("http://localhost:8082/productos")
                .retrieve()
                .body(Producto[].class);

        model.addAttribute("pedido", new Pedido());

        model.addAttribute("pedidos",
                Arrays.asList(pedidos));

        model.addAttribute("usuarios",
                Arrays.asList(usuarios));

        model.addAttribute("productos",
                Arrays.asList(productos));

        return "pedidos";
    }

    @GetMapping("/pedidos/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Pedido pedido = restClient.get()
                .uri("http://localhost:8083/pedidos/" + id)
                .retrieve()
                .body(Pedido.class);

        Pedido[] pedidos = restClient.get()
                .uri("http://localhost:8083/pedidos")
                .retrieve()
                .body(Pedido[].class);

        Usuario[] usuarios = restClient.get()
                .uri("http://localhost:8081/usuarios")
                .retrieve()
                .body(Usuario[].class);

        Producto[] productos = restClient.get()
                .uri("http://localhost:8082/productos")
                .retrieve()
                .body(Producto[].class);

        model.addAttribute("pedido", pedido);
        model.addAttribute("pedidos", Arrays.asList(pedidos));
        model.addAttribute("usuarios", Arrays.asList(usuarios));
        model.addAttribute("productos", Arrays.asList(productos));

        return "pedidos";
    }

    @PostMapping("/pedidos/guardar")
    public String guardar(
            @ModelAttribute Pedido pedido) {

        if (pedido.getId() == null) {

            restClient.post()
                    .uri("http://localhost:8083/pedidos")
                    .body(pedido)
                    .retrieve()
                    .toBodilessEntity();

        } else {

            restClient.put()
                    .uri("http://localhost:8083/pedidos/" + pedido.getId())
                    .body(pedido)
                    .retrieve()
                    .toBodilessEntity();
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/pedidos/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        restClient.delete()
                .uri("http://localhost:8083/pedidos/" + id)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/pedidos";
    }
}