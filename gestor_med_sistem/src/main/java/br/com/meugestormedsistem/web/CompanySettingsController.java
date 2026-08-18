package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.CompanySettings;
import br.com.meugestormedsistem.service.CompanySettingsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/company-settings")
public class CompanySettingsController {
    private final CompanySettingsService service;

    public CompanySettingsController(CompanySettingsService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompanySettings> listar() {
        return service.findAll();
    }

    @GetMapping("/{companyId}")
    public CompanySettings buscar(@PathVariable UUID companyId) {
        return encontrar(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanySettings criar(@Valid @RequestBody Request body) {
        if (service.existsById(body.companyId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Configuração já existe");
        return service.save(preencher(new CompanySettings(), body));
    }

    @PutMapping("/{companyId}")
    public CompanySettings atualizar(@PathVariable UUID companyId, @Valid @RequestBody Request body) {
        CompanySettings s = preencher(encontrar(companyId), body);
        s.setCompanyId(companyId);
        return service.save(s);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID companyId) {
        service.delete(encontrar(companyId));
    }

    private CompanySettings encontrar(UUID id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuração não encontrada"));
    }

    private CompanySettings preencher(CompanySettings s, Request r) {
        s.setCompanyId(r.companyId());
        s.setTimezone(r.timezone());
        s.setLocale(r.locale());
        s.setCurrency(r.currency());
        s.setSettings(r.settings());
        return s;
    }

    public record Request(@NotNull UUID companyId, @NotBlank String timezone, @NotBlank String locale,
            @Pattern(regexp = "[A-Z]{3}") String currency, @NotNull Map<String, Object> settings) {
    }
}
