package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.Company; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanyRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/companies")
public class CompanyController extends AbstractCrudController<Company> {
 private final CrudService<Company,java.util.UUID> service; public CompanyController(CompanyRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,Company::setId);} protected CrudService<Company,java.util.UUID> service(){return service;}
}
