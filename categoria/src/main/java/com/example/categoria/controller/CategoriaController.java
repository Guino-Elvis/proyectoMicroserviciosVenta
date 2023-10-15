package com.example.categoria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.categoria.entity.Categoria;
import com.example.categoria.service.CategoriaService;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<List<Categoria>> list() {
        return ResponseEntity.ok().body(categoriaService.listar());
    }

    @PostMapping()
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardar(categoria));
    }

    @PutMapping()
    public ResponseEntity<Categoria> update(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.actualizar(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(categoriaService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        categoriaService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }
}
