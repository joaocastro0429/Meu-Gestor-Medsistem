package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.SaasModule; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.SaasModuleRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/modules")
public class SaasModuleController extends AbstractCrudController<SaasModule> {
 private final CrudService<SaasModule,java.util.UUID> service; public SaasModuleController(SaasModuleRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,SaasModule::setId);} protected CrudService<SaasModule,java.util.UUID> service(){return service;}
}
