package com.example.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.venta.entity.Venta;
import com.example.venta.service.VentaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @CircuitBreaker(name = "ListCB", fallbackMethod = "fallBackList")
    @GetMapping()
    public ResponseEntity<List<Venta>> list() {
        return ResponseEntity.ok().body(ventaService.listar());
    }

    @CircuitBreaker(name = "saveCB", fallbackMethod = "fallBacksave")
    @PostMapping()
    public ResponseEntity<Venta> save(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.guardar(venta));
    }

    @CircuitBreaker(name = "updateCB", fallbackMethod = "fallBackupdate")
    @PutMapping()
    public ResponseEntity<Venta> update(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.actualizar(venta));
    }

    @CircuitBreaker(name = "listByIdCB", fallbackMethod = "fallBacklistById")
    @GetMapping("/{id}")
    public ResponseEntity<Venta> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(ventaService.listarPorId(id).get());
    }

    @CircuitBreaker(name = "deleteByIdCB", fallbackMethod = "fallBackdeleteById")
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        ventaService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }
}
