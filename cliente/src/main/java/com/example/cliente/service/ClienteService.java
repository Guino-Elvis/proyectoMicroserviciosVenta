package com.example.cliente.service;

import java.util.List;
import java.util.Optional;

import com.example.cliente.entity.Cliente;

public interface ClienteService {

    public List<Cliente> listar();

    public Cliente guardar(Cliente cliente);

    public Cliente actualizar(Cliente cliente);

    public Optional<Cliente> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
