package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.service.SaasModuleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/modules")
public class SaasModuleController {
    private final SaasModuleService service;

    public SaasModuleController(SaasModuleService service) {
        this.service = service;
    }

    @GetMapping
    public List<SaasModule> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SaasModule buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @GetMapping("/code/{code}")
    public SaasModule buscarPorCodigo(@PathVariable String code) {
        return service.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaasModule criar(@Valid @RequestBody Request body) {
        return service.criar(preencher(new SaasModule(), body));
    }

    @PutMapping("/{id}")
    public SaasModule atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return service.atualizar(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public SaasModule alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        SaasModule m = encontrar(id);
        m.setStatus(body.status());
        return service.save(m);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        service.delete(encontrar(id));
    }

    private SaasModule encontrar(UUID id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));
    }

    private SaasModule preencher(SaasModule m, Request r) {
        m.setCode(r.code());
        m.setName(r.name());
        m.setStatus(r.status());
        return m;
    }

    public record Request(@NotBlank String code, @NotBlank String name, @NotNull Status status) {
    }

    public record StatusRequest(@NotNull Status status) {
    }
}
