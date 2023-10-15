package com.example.venta.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.entity.Venta;
import com.example.venta.service.VentaService;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping()
    public ResponseEntity<List<Venta>> list() {
        return ResponseEntity.ok().body(ventaService.listar());
    }

    @PostMapping()
    public ResponseEntity<Venta> save(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.guardar(venta));
    }

    @PutMapping()
    public ResponseEntity<Venta> update(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.actualizar(venta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(ventaService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        ventaService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }
}
