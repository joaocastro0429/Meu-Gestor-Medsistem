package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.AppUser; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {}
