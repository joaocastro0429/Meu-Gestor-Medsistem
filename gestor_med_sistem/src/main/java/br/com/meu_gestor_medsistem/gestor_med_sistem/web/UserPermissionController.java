package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.UserPermissionRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*; import java.util.UUID;
@RestController @RequestMapping("/api/user-permissions")
public class UserPermissionController extends AbstractCompositeCrudController<UserPermission,UserPermissionId>{
 private final CrudService<UserPermission,UserPermissionId> service; public UserPermissionController(UserPermissionRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,UserPermission::setId);} protected CrudService<UserPermission,UserPermissionId> service(){return service;} protected UserPermissionId key(UUID a,UUID b){return new UserPermissionId(a,b);}
}
