package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.SaasPlan;
import br.com.meugestormedsistem.repository.SaasPlanRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SaasPlanService {
    private final SaasPlanRepository repository;

    public SaasPlanService(SaasPlanRepository repository) {
        this.repository = repository;
    }

    public List<SaasPlan> findAll() {
        return repository.findAll();
    }

    public Optional<SaasPlan> findById(UUID id) {
        return repository.findById(id);
    }

    public SaasPlan save(SaasPlan value) {
        return repository.save(value);
    }

    public void delete(SaasPlan value) {
        repository.delete(value);
    }
}
