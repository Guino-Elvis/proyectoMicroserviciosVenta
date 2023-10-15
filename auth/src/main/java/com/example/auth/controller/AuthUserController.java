package com.example.auth.controller;

import com.example.auth.dto.AuthUserDto;
import com.example.auth.dto.CreateUserResponse;
import com.example.auth.entity.AuthUser;
import com.example.auth.entity.TokenDto;
import com.example.auth.service.AuthUserService;
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

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto) {
        AuthUser authUser = authUserService.findUserByEmail(authUserDto.getEmail());

        if (authUser == null) {
            // Usuario no existe, devuelve una respuesta personalizada
            return ResponseEntity.badRequest().body(new TokenDto("Usuario no existe"));
        }

        // Validar la contraseña proporcionada
        if (!authUser.getPassword().equals(authUserDto.getPassword())) {
            // Contraseña incorrecta, devuelve una respuesta personalizada
            return ResponseEntity.badRequest().body(new TokenDto("Contraseña incorrecta"));
        }

        TokenDto tokenDto = authUserService.login(authUserDto);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuthUserDto authUserDto) {
        // Verificar si ya existe un usuario con el mismo correo electrónico
        AuthUser existingUser = authUserService.findUserByEmail(authUserDto.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Usuario ya existe.");
        }

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

    @GetMapping("/list")
    public ResponseEntity<List<AuthUser>> list() {
        List<AuthUser> userList = authUserService.listar();
        return ResponseEntity.ok(userList);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<AuthUser> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(authUserService.listarPorId(id).get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<AuthUser> existingUser = authUserService.listarPorId(id);
        if (!existingUser.isPresent())
            return ResponseEntity.notFound().build();

        authUserService.eliminarPorId(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}