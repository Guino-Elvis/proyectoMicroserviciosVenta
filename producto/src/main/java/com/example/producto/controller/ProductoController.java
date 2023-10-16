package com.example.producto.controller;

import com.example.producto.entity.Producto;
import com.example.producto.service.ProductoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @CircuitBreaker(name = "ListCB", fallbackMethod = "fallBackList")
    @GetMapping()
    public ResponseEntity<List<Producto>> list() {
        return ResponseEntity.ok().body(productoService.listar());
    }

    @CircuitBreaker(name = "saveCB", fallbackMethod = "fallBacksave")
    @PostMapping()
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardar(producto));
    }

    @CircuitBreaker(name = "updateCB", fallbackMethod = "fallBackupdate")
    @PutMapping()
    public ResponseEntity<Producto> update(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(producto));
    }

    @CircuitBreaker(name = "listByIdCB", fallbackMethod = "fallBacklistById")
    @GetMapping("/{id}")
    public ResponseEntity<Producto> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(productoService.listarPorId(id).get());
    }

    @CircuitBreaker(name = "deleteByIdCB", fallbackMethod = "fallBackdeleteById")
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        productoService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }
}
