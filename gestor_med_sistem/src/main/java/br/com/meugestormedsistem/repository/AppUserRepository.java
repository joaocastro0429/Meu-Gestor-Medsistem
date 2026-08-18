package br.com.meugestormedsistem.repository;

import br.com.meugestormedsistem.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByEmailValueIgnoreCase(String email);
    boolean existsByEmailValueIgnoreCase(String email);
}
