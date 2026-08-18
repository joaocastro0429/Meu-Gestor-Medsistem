package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.SaasPlan; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface SaasPlanRepository extends JpaRepository<SaasPlan, UUID> {}
