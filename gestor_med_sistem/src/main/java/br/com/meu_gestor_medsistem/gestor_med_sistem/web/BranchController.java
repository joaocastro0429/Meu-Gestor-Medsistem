package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.Branch; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.BranchRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/branches")
public class BranchController extends AbstractCrudController<Branch> {
 private final CrudService<Branch,java.util.UUID> service; public BranchController(BranchRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,Branch::setId);} protected CrudService<Branch,java.util.UUID> service(){return service;}
}
