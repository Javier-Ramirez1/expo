package com.example.frontendservice.controller;

import com.example.frontendservice.dto.Pedido;
import com.example.frontendservice.dto.Producto;
import com.example.frontendservice.dto.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

@Controller
public class HomeController {

    private final RestClient restClient = RestClient.create();

    @GetMapping("/")
    public String inicio(Model model) {

        Usuario[] usuarios = restClient.get()
                .uri("http://usuarios-service:8081/usuarios")
                .retrieve()
                .body(Usuario[].class);

        Producto[] productos = restClient.get()
                .uri("http://productos-service:8082/productos")
                .retrieve()
                .body(Producto[].class);

        Pedido[] pedidos = restClient.get()
                .uri("http://pedidos-service:8083/pedidos")
                .retrieve()
                .body(Pedido[].class);

        model.addAttribute("totalUsuarios", usuarios.length);
        model.addAttribute("totalProductos", productos.length);
        model.addAttribute("totalPedidos", pedidos.length);

        return "index";
    }
}