package com.example.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.venta.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

}
