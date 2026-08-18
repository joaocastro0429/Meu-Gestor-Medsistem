package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.PasswordResetToken;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.PasswordResetTokenRepository;
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
    private final PasswordResetTokenRepository repository;

    public PasswordResetTokenController(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PasswordResetToken> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public PasswordResetToken buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PasswordResetToken criar(@Valid @RequestBody Request body) {
        return repository.save(preencher(new PasswordResetToken(), body));
    }

    @PatchMapping("/{id}/used")
    public PasswordResetToken marcarUsado(@PathVariable UUID id) {
        PasswordResetToken t = encontrar(id);
        t.setUsedAt(Instant.now());
        return repository.save(t);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private PasswordResetToken encontrar(UUID id) {
        return repository.findById(id)
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
