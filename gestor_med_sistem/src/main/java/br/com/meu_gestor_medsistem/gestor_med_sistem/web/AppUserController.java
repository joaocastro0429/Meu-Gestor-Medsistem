package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.AppUser; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.AppUserRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/users")
public class AppUserController extends AbstractCrudController<AppUser> {
 private final CrudService<AppUser,java.util.UUID> service; public AppUserController(AppUserRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,AppUser::setId);} protected CrudService<AppUser,java.util.UUID> service(){return service;}
}
