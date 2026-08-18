package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.CompanySubscription; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface CompanySubscriptionRepository extends JpaRepository<CompanySubscription, UUID> {}
