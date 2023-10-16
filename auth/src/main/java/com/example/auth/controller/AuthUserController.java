package com.example.auth.controller;

import com.example.auth.dto.AuthUserDto;
import com.example.auth.dto.CreateUserResponse;
import com.example.auth.entity.AuthUser;
import com.example.auth.entity.TokenDto;
import com.example.auth.service.AuthUserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthUserService authUserService;

    @CircuitBreaker(name = "loginCB", fallbackMethod = "fallBackLogin")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto) {
        TokenDto tokenDto = authUserService.login(authUserDto);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @CircuitBreaker(name = "validateCB", fallbackMethod = "fallBackValidate")
    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @CircuitBreaker(name = "createCB", fallbackMethod = "fallBackCreate")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuthUserDto authUserDto) {
        if (!authUserService.isPasswordConfirmed(authUserDto)) {
            return ResponseEntity.badRequest().body("Password and confirm password do not match.");
        }

        AuthUser authUser = authUserService.save(authUserDto);
        if (authUser == null) {
            return ResponseEntity.badRequest().build();
        }

        CreateUserResponse response = new CreateUserResponse();
        response.setMessage("User created successfully.");
        response.setUser(authUser);

        return ResponseEntity.ok(response);
    }

    @CircuitBreaker(name = "listCB", fallbackMethod = "fallBackList")
    @GetMapping("/list")
    public ResponseEntity<List<AuthUser>> list() {
        List<AuthUser> userList = authUserService.listar();
        return ResponseEntity.ok(userList);
    }

    @CircuitBreaker(name = "updateCB", fallbackMethod = "fallBackUpdate")
    @PutMapping("/update/{id}")
    public ResponseEntity<AuthUser> update(@PathVariable Integer id, @RequestBody AuthUserDto authUserDto) {
        Optional<AuthUser> existingUser = authUserService.listarPorId(id);
        if (!existingUser.isPresent())
            return ResponseEntity.notFound().build();

        AuthUser updatedUser = existingUser.get();
        updatedUser.setEmail(authUserDto.getEmail());
        updatedUser.setPassword(authUserDto.getPassword());
        updatedUser.setRole(authUserDto.getRole());
        updatedUser.setName(authUserDto.getName());
        updatedUser.setConfirmPassword(authUserDto.getConfirmPassword());

        AuthUser savedUser = authUserService.actualizar(updatedUser);
        return ResponseEntity.ok(savedUser);
    }

    @CircuitBreaker(name = "listByIdCB", fallbackMethod = "fallBackListById")
    @GetMapping("/{id}")
    public ResponseEntity<AuthUser> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(authUserService.listarPorId(id).get());
    }

    @CircuitBreaker(name = "deleteCB", fallbackMethod = "fallBackDelete")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<AuthUser> existingUser = authUserService.listarPorId(id);
        if (!existingUser.isPresent())
            return ResponseEntity.notFound().build();

        authUserService.eliminarPorId(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}