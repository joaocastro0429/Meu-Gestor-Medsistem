package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.CompanySubscription;
import br.com.meugestormedsistem.repository.CompanySubscriptionRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanySubscriptionService {
    private final CompanySubscriptionRepository repository;

    public CompanySubscriptionService(CompanySubscriptionRepository repository) {
        this.repository = repository;
    }

    public List<CompanySubscription> findAll() {
        return repository.findAll();
    }

    public Optional<CompanySubscription> findById(UUID id) {
        return repository.findById(id);
    }

    public CompanySubscription save(CompanySubscription value) {
        return repository.save(value);
    }

    public void delete(CompanySubscription value) {
        repository.delete(value);
    }
}
