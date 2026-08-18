package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.PasswordResetToken;
import br.com.meugestormedsistem.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository repository;

    public PasswordResetTokenService(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    public List<PasswordResetToken> findAll() {
        return repository.findAll();
    }

    public Optional<PasswordResetToken> findById(UUID id) {
        return repository.findById(id);
    }

    public PasswordResetToken save(PasswordResetToken value) {
        return repository.save(value);
    }

    public void delete(PasswordResetToken value) {
        repository.delete(value);
    }
}
