package br.com.meu_gestor_medsistem.gestor_med_sistem.web;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.CompanySubscription; import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.CompanySubscriptionRepository; import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService; import tools.jackson.databind.ObjectMapper; import jakarta.validation.Validator; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/subscriptions")
public class CompanySubscriptionController extends AbstractCrudController<CompanySubscription> {
 private final CrudService<CompanySubscription,java.util.UUID> service; public CompanySubscriptionController(CompanySubscriptionRepository r,ObjectMapper m,Validator v){service=new CrudService<>(r,m,v,CompanySubscription::setId);} protected CrudService<CompanySubscription,java.util.UUID> service(){return service;}
}
