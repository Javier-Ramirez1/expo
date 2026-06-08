package com.example.usuariosservice.service;

import com.example.usuariosservice.entitid.Usuario;
import com.example.usuariosservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario guardar(Usuario usuario) {

        if (usuario.getNombre() == null ||
                usuario.getNombre().trim().isEmpty()) {

            throw new RuntimeException("Nombre obligatorio");
        }

        if (usuario.getCorreo() == null ||
                usuario.getCorreo().trim().isEmpty()) {

            throw new RuntimeException("Correo obligatorio");
        }

        return repository.save(usuario);
    }

    public Usuario buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}