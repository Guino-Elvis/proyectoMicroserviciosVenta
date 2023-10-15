package com.example.auth.service.impl;

import com.example.auth.dto.AuthUserDto;
import com.example.auth.entity.AuthUser;
import com.example.auth.entity.TokenDto;
import com.example.auth.repository.AuthRepository;
import com.example.auth.security.JwtProvider;
import com.example.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public AuthUser save(AuthUserDto authUserDto) {
        Optional<AuthUser> user = authRepository.findByEmail(authUserDto.getEmail());
        if (user.isPresent())
            return null;
        String password = passwordEncoder.encode(authUserDto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .email(authUserDto.getEmail())
                .password(password)
                .build();

        return authRepository.save(authUser);
    }

    @Override
    public TokenDto login(AuthUserDto authUserDto) {
        Optional<AuthUser> user = authRepository.findByEmail(authUserDto.getEmail());
        if (!user.isPresent())
            return null;
        if (passwordEncoder.matches(authUserDto.getPassword(), user.get().getPassword()))
            return new TokenDto(jwtProvider.createToken(user.get()));
        return null;
    }

    @Override
    public TokenDto validate(String token) {
        if (!jwtProvider.validate(token))
            return null;
        String email = jwtProvider.getEmailFromToken(token);
        if (!authRepository.findByEmail(email).isPresent())
            return null;

        return new TokenDto(token);
    }

    @Override
    public List<AuthUser> listar() {
        return authRepository.findAll();
    }

    @Override
    public AuthUser actualizar(AuthUser authUser) {
        return authRepository.save(authUser);
    }

    @Override
    public Optional<AuthUser> listarPorId(Integer id) {
        return authRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        authRepository.deleteById(id);
    }

    // confirmar contraseña
    @Override
    public boolean isPasswordConfirmed(AuthUserDto authUserDto) {
        return authUserDto.getPassword().equals(authUserDto.getConfirmPassword());
    }

}