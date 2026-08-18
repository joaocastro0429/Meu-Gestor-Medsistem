package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.CompanySettings; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanySettingsRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/company-settings")
public class CompanySettingsController extends AbstractCrudController<CompanySettings> {
 private final CrudService<CompanySettings,java.util.UUID> service; public CompanySettingsController(CompanySettingsRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,CompanySettings::setCompanyId);} protected CrudService<CompanySettings,java.util.UUID> service(){return service;}
}
