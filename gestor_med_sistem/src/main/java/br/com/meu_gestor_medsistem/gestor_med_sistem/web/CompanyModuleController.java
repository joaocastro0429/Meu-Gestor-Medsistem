package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanyModuleRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*; import java.util.UUID;
@RestController @RequestMapping("/api/company-modules")
public class CompanyModuleController extends AbstractCompositeCrudController<CompanyModule,CompanyModuleId>{
 private final CrudService<CompanyModule,CompanyModuleId> service; public CompanyModuleController(CompanyModuleRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,CompanyModule::setId);} protected CrudService<CompanyModule,CompanyModuleId> service(){return service;} protected CompanyModuleId key(UUID a,UUID b){return new CompanyModuleId(a,b);}
}
