package br.com.meu_gestor_medsistem.gestor_med_sistem.repository;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface CompanyModuleRepository extends JpaRepository<CompanyModule, CompanyModuleId> {}
