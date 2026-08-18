package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface UserPermissionRepository extends JpaRepository<UserPermission, UserPermissionId> {}
