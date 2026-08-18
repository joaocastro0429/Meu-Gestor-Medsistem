package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.SaasModule;
import br.com.meugestormedsistem.repository.SaasModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class SaasModuleService {
    private final SaasModuleRepository repository;

    public SaasModuleService(SaasModuleRepository repository) { this.repository = repository; }
    public List<SaasModule> findAll() { return repository.findAll(); }
    public Optional<SaasModule> findById(UUID id) { return repository.findById(id); }
    public Optional<SaasModule> findByCodeIgnoreCase(String code) { return repository.findByCodeIgnoreCase(normalizar(code)); }

    public SaasModule criar(SaasModule module) {
        module.setCode(normalizar(module.getCode()));
        if (repository.existsByCodeIgnoreCase(module.getCode())) throw duplicado(module.getCode());
        return repository.save(module);
    }

    public SaasModule atualizar(SaasModule module) {
        module.setCode(normalizar(module.getCode()));
        if (repository.existsByCodeIgnoreCaseAndIdNot(module.getCode(), module.getId())) throw duplicado(module.getCode());
        return repository.save(module);
    }

    public SaasModule save(SaasModule module) { return repository.save(module); }
    public void delete(SaasModule module) { repository.delete(module); }
    private String normalizar(String code) { return code.trim().toUpperCase(Locale.ROOT); }
    private ResponseStatusException duplicado(String code) { return new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um módulo com o código " + code); }
}
