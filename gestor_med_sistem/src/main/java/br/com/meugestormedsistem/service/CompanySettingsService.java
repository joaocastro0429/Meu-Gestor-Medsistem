package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.CompanySettings;
import br.com.meugestormedsistem.repository.CompanySettingsRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanySettingsService {
    private final CompanySettingsRepository repository;

    public CompanySettingsService(CompanySettingsRepository repository) {
        this.repository = repository;
    }

    public List<CompanySettings> findAll() {
        return repository.findAll();
    }

    public Optional<CompanySettings> findById(UUID id) {
        return repository.findById(id);
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    public CompanySettings save(CompanySettings value) {
        return repository.save(value);
    }

    public void delete(CompanySettings value) {
        repository.delete(value);
    }
}
