package br.com.meu_gestor_medsistem.gestor_med_sistem.repository;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.PasswordResetToken; import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {}
