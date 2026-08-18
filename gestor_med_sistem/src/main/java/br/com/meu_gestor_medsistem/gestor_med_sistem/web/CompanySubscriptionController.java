package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanySubscriptionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/subscriptions")
public class CompanySubscriptionController {
    private final CompanySubscriptionRepository repository;

    public CompanySubscriptionController(CompanySubscriptionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CompanySubscription> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public CompanySubscription buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanySubscription criar(@Valid @RequestBody Request body) {
        return repository.save(preencher(new CompanySubscription(), body));
    }

    @PutMapping("/{id}")
    public CompanySubscription atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return repository.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public CompanySubscription alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        CompanySubscription s = encontrar(id);
        s.setStatus(body.status());
        return repository.save(s);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private CompanySubscription encontrar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assinatura não encontrada"));
    }

    private CompanySubscription preencher(CompanySubscription s, Request r) {
        s.setCompanyId(r.companyId());
        s.setPlanId(r.planId());
        s.setPrice(r.price());
        s.setStartsOn(r.startsOn());
        s.setExpiresOn(r.expiresOn());
        s.setStatus(r.status());
        return s;
    }

    public record Request(@NotNull UUID companyId, @NotNull UUID planId, @NotNull @DecimalMin("0.00") BigDecimal price,
            @NotNull LocalDate startsOn, @NotNull LocalDate expiresOn, @NotNull SubscriptionStatus status) {
    }

    public record StatusRequest(@NotNull SubscriptionStatus status) {
    }
}
