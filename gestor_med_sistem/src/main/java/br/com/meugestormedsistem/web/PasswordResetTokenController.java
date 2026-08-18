package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.PasswordResetToken;
import br.com.meugestormedsistem.service.PasswordResetTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/password-reset-tokens")
public class PasswordResetTokenController {
    private final PasswordResetTokenService service;

    public PasswordResetTokenController(PasswordResetTokenService service) {
        this.service = service;
    }

    @GetMapping
    public List<PasswordResetToken> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PasswordResetToken buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PasswordResetToken criar(@Valid @RequestBody Request body) {
        return service.save(preencher(new PasswordResetToken(), body));
    }

    @PatchMapping("/{id}/used")
    public PasswordResetToken marcarUsado(@PathVariable UUID id) {
        PasswordResetToken t = encontrar(id);
        t.setUsedAt(Instant.now());
        return service.save(t);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        service.delete(encontrar(id));
    }

    private PasswordResetToken encontrar(UUID id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token não encontrado"));
    }

    private PasswordResetToken preencher(PasswordResetToken t, Request r) {
        t.setUserId(r.userId());
        t.setTokenHash(r.tokenHash());
        t.setExpiresAt(r.expiresAt());
        return t;
    }

    public record Request(@NotNull UUID userId, @NotBlank String tokenHash, @NotNull @Future Instant expiresAt) {
    }
}
