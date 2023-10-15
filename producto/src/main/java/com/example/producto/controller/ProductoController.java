package com.example.producto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producto.entity.Producto;
import com.example.producto.service.ProductoService;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping()
    public ResponseEntity<List<Producto>> list() {
        return ResponseEntity.ok().body(productoService.listar());
    }

    @PostMapping()
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardar(producto));
    }

    @PutMapping()
    public ResponseEntity<Producto> update(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(productoService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        productoService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }
}
