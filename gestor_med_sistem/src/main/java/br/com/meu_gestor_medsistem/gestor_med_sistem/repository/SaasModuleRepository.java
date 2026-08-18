package br.com.meu_gestor_medsistem.gestor_med_sistem.repository;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.SaasModule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SaasModuleRepository extends JpaRepository<SaasModule, UUID> {
    boolean existsByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCaseAndIdNot(String code, UUID id);
    Optional<SaasModule> findByCodeIgnoreCase(String code);
}
