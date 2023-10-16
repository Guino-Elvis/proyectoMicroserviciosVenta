package com.example.venta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.venta.dto.Cliente;
import com.example.venta.entity.Venta;
import com.example.venta.service.VentaService;
import java.util.Collections;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;

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

    @CircuitBreaker(name = "listByIdCB", fallbackMethod = "fallBacklistById")
    @GetMapping("/{id}")
    public ResponseEntity<Venta> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(ventaService.listarPorId(id).get());
    }

    @CircuitBreaker(name = "deleteByIdCB", fallbackMethod = "fallBackDeleteById")
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        ventaService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }

    // resilencia
    private ResponseEntity<Venta> fallBacklistById(@PathVariable(required = true) Integer id, RuntimeException e) {
        Venta venta = new Venta();
        venta.setId(90000);
        return ResponseEntity.ok().body(venta);
    }

    private ResponseEntity<String> fallBackDeleteById(@PathVariable(required = true) Integer id, RuntimeException e) {
        // Aquí puedes devolver una respuesta alternativa en caso de error, por ejemplo:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el registro.");
    }

}
