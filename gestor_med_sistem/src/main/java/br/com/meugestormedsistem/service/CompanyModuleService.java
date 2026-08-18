package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.repository.CompanyModuleRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanyModuleService {
    private final CompanyModuleRepository repository;

    public CompanyModuleService(CompanyModuleRepository repository) {
        this.repository = repository;
    }

    public List<CompanyModule> findAll() {
        return repository.findAll();
    }

    public Optional<CompanyModule> findById(CompanyModuleId id) {
        return repository.findById(id);
    }

    public boolean existsById(CompanyModuleId id) {
        return repository.existsById(id);
    }

    public CompanyModule save(CompanyModule v) {
        return repository.save(v);
    }

    public void deleteById(CompanyModuleId id) {
        repository.deleteById(id);
    }
}
