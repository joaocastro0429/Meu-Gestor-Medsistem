package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.*;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.SaasPlanModuleRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/plan-modules")
public class SaasPlanModuleController {
    private final SaasPlanModuleRepository repository;
    public SaasPlanModuleController(SaasPlanModuleRepository repository) { this.repository = repository; }

    @GetMapping public List<SaasPlanModule> listar() { return repository.findAll(); }

    @GetMapping("/{planId}/{moduleId}")
    public SaasPlanModule get(@PathVariable UUID planId, @PathVariable UUID moduleId) {
        return repository.findById(new SaasPlanModuleId(planId,moduleId)).orElseThrow(this::notFound);
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public SaasPlanModule create(@Valid @RequestBody Request body) {
        SaasPlanModule entity=new SaasPlanModule(); entity.setId(new SaasPlanModuleId(body.planId(),body.moduleId())); return repository.save(entity);
    }

    @PutMapping("/{planId}/{moduleId}")
    public SaasPlanModule replace(@PathVariable UUID planId,@PathVariable UUID moduleId) {
        SaasPlanModuleId id=new SaasPlanModuleId(planId,moduleId); if(!repository.existsById(id)) throw notFound();
        SaasPlanModule entity=new SaasPlanModule(); entity.setId(id); return repository.save(entity);
    }

    @DeleteMapping("/{planId}/{moduleId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID planId,@PathVariable UUID moduleId) {
        SaasPlanModuleId id=new SaasPlanModuleId(planId,moduleId); if(!repository.existsById(id)) throw notFound(); repository.deleteById(id);
    }
    private ResponseStatusException notFound(){return new ResponseStatusException(HttpStatus.NOT_FOUND,"Vínculo não encontrado");}
    public record Request(@NotNull UUID planId,@NotNull UUID moduleId){}
}
