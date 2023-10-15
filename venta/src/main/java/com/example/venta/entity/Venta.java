package com.example.venta.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.venta.dto.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String serie;
    private String numero;
    private String codigo;
    private Integer clienteId;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at = LocalDateTime.now();

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inscripccion_id")
    private List<VentaDetalle> detalle;

    private Cliente cliente;
}
