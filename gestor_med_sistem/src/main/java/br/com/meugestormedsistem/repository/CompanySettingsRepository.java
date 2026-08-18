package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.CompanySettings; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface CompanySettingsRepository extends JpaRepository<CompanySettings, UUID> {}
