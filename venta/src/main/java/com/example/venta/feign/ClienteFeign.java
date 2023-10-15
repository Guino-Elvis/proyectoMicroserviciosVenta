package com.example.venta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.venta.dto.Cliente;

@FeignClient(name = "cliente-service", path = "/cliente")
public interface ClienteFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listById(@PathVariable(required = true) Integer id);
}
