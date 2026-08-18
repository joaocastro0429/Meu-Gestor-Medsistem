package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface UserBranchRepository extends JpaRepository<UserBranch, UserBranchId> {}
