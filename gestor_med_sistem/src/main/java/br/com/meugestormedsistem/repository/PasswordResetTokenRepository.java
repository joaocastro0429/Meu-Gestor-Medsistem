package br.com.meugestormedsistem.repository;
import br.com.meugestormedsistem.domain.PasswordResetToken; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {}
