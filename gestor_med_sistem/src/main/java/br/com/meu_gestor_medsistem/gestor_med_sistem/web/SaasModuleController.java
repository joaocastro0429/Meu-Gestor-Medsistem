package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.SaasModuleRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/modules")
public class SaasModuleController {
    private final SaasModuleRepository repository;

    public SaasModuleController(SaasModuleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SaasModule> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public SaasModule buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @GetMapping("/code/{code}")
    public SaasModule buscarPorCodigo(@PathVariable String code) {
        return repository.findByCodeIgnoreCase(normalizarCodigo(code))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaasModule criar(@Valid @RequestBody Request body) {
        validarCodigoNovo(body.code());
        return repository.save(preencher(new SaasModule(), body));
    }

    @PutMapping("/{id}")
    public SaasModule atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        validarCodigoNaAtualizacao(body.code(), id);
        return repository.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public SaasModule alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        SaasModule m = encontrar(id);
        m.setStatus(body.status());
        return repository.save(m);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private SaasModule encontrar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));
    }

    private SaasModule preencher(SaasModule m, Request r) {
        m.setCode(normalizarCodigo(r.code()));
        m.setName(r.name());
        m.setStatus(r.status());
        return m;
    }

    private void validarCodigoNovo(String code) {
        if (repository.existsByCodeIgnoreCase(normalizarCodigo(code))) {
            throw codigoDuplicado(code);
        }
    }

    private void validarCodigoNaAtualizacao(String code, UUID id) {
        if (repository.existsByCodeIgnoreCaseAndIdNot(normalizarCodigo(code), id)) {
            throw codigoDuplicado(code);
        }
    }

    private String normalizarCodigo(String code) {
        return code.trim().toUpperCase(Locale.ROOT);
    }

    private ResponseStatusException codigoDuplicado(String code) {
        return new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe um módulo com o código " + normalizarCodigo(code));
    }

    public record Request(@NotBlank String code, @NotBlank String name, @NotNull Status status) {
    }

    public record StatusRequest(@NotNull Status status) {
    }
}
