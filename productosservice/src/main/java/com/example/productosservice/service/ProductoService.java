package com.example.productosservice.service;

import com.example.productosservice.entity.Producto;
import com.example.productosservice.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto guardar(Producto producto) {

        if (producto.getPrecio() <= 0) {
            throw new RuntimeException("Precio inválido");
        }

        if (producto.getStock() < 0) {
            throw new RuntimeException("Stock inválido");
        }

        return repository.save(producto);
    }

    public Producto buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
    public Producto actualizarStock(
            Long id,
            Integer stock) {

        Producto producto =
                repository.findById(id)
                        .orElseThrow();

        producto.setStock(stock);

        return repository.save(producto);
    }
}