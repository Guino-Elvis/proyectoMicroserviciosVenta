package com.example.venta.dto;

import lombok.Data;

@Data
public class Cliente {
    private Integer id;
    private String nombre;
    private String dni;
    private String celular;
    private String telefono;
    private String apellidopaterno;
    private String apellidomaterno;
}
