package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Company> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Company buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company criar(@Valid @RequestBody Request body) {
        return service.save(preencher(new Company(), body));
    }

    @PutMapping("/{id}")
    public Company atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return service.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public Company alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        Company c = encontrar(id);
        c.setStatus(body.status());
        return service.save(c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        service.delete(encontrar(id));
    }

    private Company encontrar(UUID id) {
        return service.findById(id)
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
