package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanyModuleRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/company-modules")
public class CompanyModuleController {
    private final CompanyModuleRepository repository;

    public CompanyModuleController(CompanyModuleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CompanyModule> listar() {
        return repository.findAll();
    }

    @GetMapping("/{companyId}/{moduleId}")
    public CompanyModule get(@PathVariable UUID companyId, @PathVariable UUID moduleId) {
        return repository.findById(key(companyId, moduleId)).orElseThrow(this::notFound);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyModule create(@Valid @RequestBody Request body) {
        CompanyModule e = new CompanyModule();
        e.setId(key(body.companyId(), body.moduleId()));
        e.setEnabled(body.enabled());
        return repository.save(e);
    }

    @PutMapping("/{companyId}/{moduleId}")
    public CompanyModule put(@PathVariable UUID companyId, @PathVariable UUID moduleId,
            @Valid @RequestBody Request body) {
        CompanyModuleId id = key(companyId, moduleId);
        if (!repository.existsById(id))
            throw notFound();
        CompanyModule e = new CompanyModule();
        e.setId(id);
        e.setEnabled(body.enabled());
        return repository.save(e);
    }

    @PatchMapping("/{companyId}/{moduleId}")
    public CompanyModule patch(@PathVariable UUID companyId, @PathVariable UUID moduleId,
            @RequestBody Map<String, Boolean> body) {
        CompanyModule e = get(companyId, moduleId);
        if (body.containsKey("enabled"))
            e.setEnabled(body.get("enabled"));
        return repository.save(e);
    }

    @DeleteMapping("/{companyId}/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID companyId, @PathVariable UUID moduleId) {
        CompanyModuleId id = key(companyId, moduleId);
        if (!repository.existsById(id))
            throw notFound();
        repository.deleteById(id);
    }

    private CompanyModuleId key(UUID a, UUID b) {
        return new CompanyModuleId(a, b);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Vínculo não encontrado");
    }

    public record Request(@NotNull UUID companyId, @NotNull UUID moduleId, boolean enabled) {
    }
}
