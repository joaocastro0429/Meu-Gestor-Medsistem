package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.service.SaasPlanService;
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
    private final SaasPlanService service;

    public SaasPlanController(SaasPlanService service) {
        this.service = service;
    }

    @GetMapping
    public List<SaasPlan> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SaasPlan buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaasPlan criar(@Valid @RequestBody Request body) {
        return service.save(preencher(new SaasPlan(), body));
    }

    @PutMapping("/{id}")
    public SaasPlan atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return service.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public SaasPlan alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        SaasPlan p = encontrar(id);
        p.setStatus(body.status());
        return service.save(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        service.delete(encontrar(id));
    }

    private SaasPlan encontrar(UUID id) {
        return service.findById(id)
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
