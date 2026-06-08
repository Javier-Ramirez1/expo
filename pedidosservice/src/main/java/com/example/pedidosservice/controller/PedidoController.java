package com.example.pedidosservice.controller;

import com.example.pedidosservice.entity.Pedido;
import com.example.pedidosservice.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @PostMapping
    public Pedido guardar(@RequestBody Pedido pedido) {
        return service.guardar(pedido);
    }
    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Pedido actualizar(
            @PathVariable Long id,
            @RequestBody Pedido pedido){

        pedido.setId(id);

        return service.guardar(pedido);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}