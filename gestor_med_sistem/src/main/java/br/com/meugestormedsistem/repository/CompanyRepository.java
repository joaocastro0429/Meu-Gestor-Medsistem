package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.Company; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface CompanyRepository extends JpaRepository<Company, UUID> {}
