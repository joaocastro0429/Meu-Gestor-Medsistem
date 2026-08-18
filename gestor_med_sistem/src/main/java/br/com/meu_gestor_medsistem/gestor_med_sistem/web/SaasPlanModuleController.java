package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.SaasPlanModuleRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*; import java.util.UUID;
@RestController @RequestMapping("/api/plan-modules")
public class SaasPlanModuleController extends AbstractCompositeCrudController<SaasPlanModule,SaasPlanModuleId>{
 private final CrudService<SaasPlanModule,SaasPlanModuleId> service; public SaasPlanModuleController(SaasPlanModuleRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,SaasPlanModule::setId);} protected CrudService<SaasPlanModule,SaasPlanModuleId> service(){return service;} protected SaasPlanModuleId key(UUID a,UUID b){return new SaasPlanModuleId(a,b);}
}
