package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.Branch;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.BranchRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchRepository repository;

    public BranchController(BranchRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Branch> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Branch buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Branch criar(@Valid @RequestBody Request body) {
        return repository.save(preencher(new Branch(), body));
    }

    @PutMapping("/{id}")
    public Branch atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return repository.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/active")
    public Branch alterarAtivo(@PathVariable UUID id, @Valid @RequestBody ActiveRequest body) {
        Branch b = encontrar(id);
        b.setActive(body.active());
        return repository.save(b);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private Branch encontrar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));
    }

    private Branch preencher(Branch b, Request r) {
        b.setCompanyId(r.companyId());
        b.setCode(r.code());
        b.setName(r.name());
        b.setActive(r.active());
        return b;
    }

    public record Request(@NotNull UUID companyId, @NotBlank String code, @NotBlank String name, boolean active) {
    }

    public record ActiveRequest(boolean active) {
    }
}
