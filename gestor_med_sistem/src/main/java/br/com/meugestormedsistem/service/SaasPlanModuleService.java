package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.repository.SaasPlanModuleRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SaasPlanModuleService {
    private final SaasPlanModuleRepository repository;

    public SaasPlanModuleService(SaasPlanModuleRepository repository) {
        this.repository = repository;
    }

    public List<SaasPlanModule> findAll() {
        return repository.findAll();
    }

    public Optional<SaasPlanModule> findById(SaasPlanModuleId id) {
        return repository.findById(id);
    }

    public boolean existsById(SaasPlanModuleId id) {
        return repository.existsById(id);
    }

    public SaasPlanModule save(SaasPlanModule v) {
        return repository.save(v);
    }

    public void deleteById(SaasPlanModuleId id) {
        repository.deleteById(id);
    }
}
