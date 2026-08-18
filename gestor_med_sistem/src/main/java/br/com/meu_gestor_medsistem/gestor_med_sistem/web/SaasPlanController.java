package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.SaasPlan; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.SaasPlanRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/plans")
public class SaasPlanController extends AbstractCrudController<SaasPlan> {
 private final CrudService<SaasPlan, java.util.UUID> service; public SaasPlanController(SaasPlanRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,SaasPlan::setId);} protected CrudService<SaasPlan,java.util.UUID> service(){return service;}
}
