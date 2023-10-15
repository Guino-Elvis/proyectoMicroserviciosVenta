package com.example.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cliente.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
