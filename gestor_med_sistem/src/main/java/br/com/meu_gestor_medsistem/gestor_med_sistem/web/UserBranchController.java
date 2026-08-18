package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.UserBranchRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*; import java.util.UUID;
@RestController @RequestMapping("/api/user-branches")
public class UserBranchController extends AbstractCompositeCrudController<UserBranch,UserBranchId>{
 private final CrudService<UserBranch,UserBranchId> service; public UserBranchController(UserBranchRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,UserBranch::setId);} protected CrudService<UserBranch,UserBranchId> service(){return service;} protected UserBranchId key(UUID a,UUID b){return new UserBranchId(a,b);}
}
