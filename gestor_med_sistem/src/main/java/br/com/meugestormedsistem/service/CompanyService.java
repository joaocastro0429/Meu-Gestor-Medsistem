package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.Company;
import br.com.meugestormedsistem.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanyService {
    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> findAll() {
        return repository.findAll();
    }

    public Optional<Company> findById(UUID id) {
        return repository.findById(id);
    }

    public Company save(Company value) {
        return repository.save(value);
    }

    public void delete(Company value) {
        repository.delete(value);
    }
}
