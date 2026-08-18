package br.com.meu_gestor_medsistem.gestor_med_sistem.repository;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.Branch; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface BranchRepository extends JpaRepository<Branch, UUID> {}
