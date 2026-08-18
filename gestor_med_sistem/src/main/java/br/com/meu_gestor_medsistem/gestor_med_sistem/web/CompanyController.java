package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanyRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyRepository repository;

    public CompanyController(CompanyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Company> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Company buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company criar(@Valid @RequestBody Request body) {
        return repository.save(preencher(new Company(), body));
    }

    @PutMapping("/{id}")
    public Company atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return repository.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public Company alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        Company c = encontrar(id);
        c.setStatus(body.status());
        return repository.save(c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private Company encontrar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada"));
    }

    private Company preencher(Company c, Request r) {
        c.setLegalName(r.legalName());
        c.setTradeName(r.tradeName());
        c.setCnpj(new Cnpj(r.cnpj()));
        c.setStatus(r.status());
        return c;
    }

    public record Request(@NotBlank String legalName, @NotBlank String tradeName,
            @NotBlank @Pattern(regexp = "\\d{14}") String cnpj, @NotNull Status status) {
    }

    public record StatusRequest(@NotNull Status status) {
    }
}
