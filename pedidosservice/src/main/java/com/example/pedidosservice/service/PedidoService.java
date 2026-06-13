package com.example.pedidosservice.service;

import com.example.pedidosservice.dto.ProductoDTO;
import com.example.pedidosservice.entity.Pedido;
import com.example.pedidosservice.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    private final RestClient restClient =
            RestClient.create();

    public PedidoService(
            PedidoRepository repository) {

        this.repository = repository;
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Pedido guardar(Pedido pedido) {

        ProductoDTO producto = restClient.get()
                .uri("http://productos-service:8082/productos/" +
                        pedido.getProductoId())
                .retrieve()
                .body(ProductoDTO.class);

        if (producto == null) {
            throw new RuntimeException(
                    "Producto no existe");
        }

        if (pedido.getCantidad() <= 0) {
            throw new RuntimeException(
                    "Cantidad inválida");
        }

        if (pedido.getCantidad() >
                producto.getStock()) {

            throw new RuntimeException(
                    "Stock insuficiente");
        }

        double total =
                producto.getPrecio()
                        * pedido.getCantidad();

        pedido.setTotal(total);

        int nuevoStock =
                producto.getStock()
                        - pedido.getCantidad();

        restClient.put()
                .uri("http://productos-service:8082/productos/stock/"
                        + producto.getId()
                        + "?stock="
                        + nuevoStock)
                .retrieve()
                .toBodilessEntity();

        return repository.save(pedido);
    }
}