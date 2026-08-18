package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.PasswordResetToken; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.PasswordResetTokenRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/password-reset-tokens")
public class PasswordResetTokenController extends AbstractCrudController<PasswordResetToken> {
 private final CrudService<PasswordResetToken,java.util.UUID> service; public PasswordResetTokenController(PasswordResetTokenRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,PasswordResetToken::setId);} protected CrudService<PasswordResetToken,java.util.UUID> service(){return service;}
}
