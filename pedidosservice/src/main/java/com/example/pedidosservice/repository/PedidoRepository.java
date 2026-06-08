package com.example.pedidosservice.repository;

import com.example.pedidosservice.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository
        extends JpaRepository<Pedido, Long> {
}