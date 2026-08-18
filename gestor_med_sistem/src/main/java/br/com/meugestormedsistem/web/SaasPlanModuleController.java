package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.service.SaasPlanModuleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/plan-modules")
public class SaasPlanModuleController {
    private final SaasPlanModuleService service;
    public SaasPlanModuleController(SaasPlanModuleService service) { this.service = service; }

    @GetMapping public List<SaasPlanModule> listar() { return service.findAll(); }

    @GetMapping("/{planId}/{moduleId}")
    public SaasPlanModule get(@PathVariable UUID planId, @PathVariable UUID moduleId) {
        return service.findById(new SaasPlanModuleId(planId,moduleId)).orElseThrow(this::notFound);
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public SaasPlanModule create(@Valid @RequestBody Request body) {
        SaasPlanModule entity=new SaasPlanModule(); entity.setId(new SaasPlanModuleId(body.planId(),body.moduleId())); return service.save(entity);
    }

    @PutMapping("/{planId}/{moduleId}")
    public SaasPlanModule replace(@PathVariable UUID planId,@PathVariable UUID moduleId) {
        SaasPlanModuleId id=new SaasPlanModuleId(planId,moduleId); if(!service.existsById(id)) throw notFound();
        SaasPlanModule entity=new SaasPlanModule(); entity.setId(id); return service.save(entity);
    }

    @DeleteMapping("/{planId}/{moduleId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID planId,@PathVariable UUID moduleId) {
        SaasPlanModuleId id=new SaasPlanModuleId(planId,moduleId); if(!service.existsById(id)) throw notFound(); service.deleteById(id);
    }
    private ResponseStatusException notFound(){return new ResponseStatusException(HttpStatus.NOT_FOUND,"Vínculo não encontrado");}
    public record Request(@NotNull UUID planId,@NotNull UUID moduleId){}
}
