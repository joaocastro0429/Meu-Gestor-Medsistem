package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.SaasPlanRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/plans")
public class SaasPlanController {
    private final SaasPlanRepository repository;

    public SaasPlanController(SaasPlanRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SaasPlan> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public SaasPlan buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaasPlan criar(@Valid @RequestBody Request body) {
        return repository.save(preencher(new SaasPlan(), body));
    }

    @PutMapping("/{id}")
    public SaasPlan atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return repository.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public SaasPlan alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        SaasPlan p = encontrar(id);
        p.setStatus(body.status());
        return repository.save(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private SaasPlan encontrar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
    }

    private SaasPlan preencher(SaasPlan p, Request r) {
        p.setName(r.name());
        p.setDefaultPrice(r.defaultPrice());
        p.setDurationDays(r.durationDays());
        p.setStatus(r.status());
        return p;
    }

    public record Request(@NotBlank String name, @NotNull @DecimalMin("0.00") BigDecimal defaultPrice,
            @Positive int durationDays, @NotNull Status status) {
    }

    public record StatusRequest(@NotNull Status status) {
    }
}
