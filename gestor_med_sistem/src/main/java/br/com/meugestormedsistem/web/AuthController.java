package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/login")
    public AuthService.Token login(@Valid @RequestBody LoginRequest body) {
        return service.login(body.email(), body.password());
    }

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password) {}
}
