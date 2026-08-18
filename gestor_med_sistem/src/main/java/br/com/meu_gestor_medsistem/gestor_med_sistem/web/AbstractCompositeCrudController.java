package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

public abstract class AbstractCompositeCrudController<T, ID> {
    protected abstract CrudService<T, ID> service();
    protected abstract ID key(UUID firstId, UUID secondId);
    @GetMapping public List<T> list(){return service().findAll();}
    @GetMapping("/{firstId}/{secondId}") public T get(@PathVariable UUID firstId,@PathVariable UUID secondId){return service().findById(key(firstId,secondId));}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public T create(@Valid @RequestBody T body){return service().create(body);}
    @PutMapping("/{firstId}/{secondId}") public T put(@PathVariable UUID firstId,@PathVariable UUID secondId,@Valid @RequestBody T body){return service().replace(key(firstId,secondId),body);}
    @PatchMapping("/{firstId}/{secondId}") public T patch(@PathVariable UUID firstId,@PathVariable UUID secondId,@RequestBody Map<String,Object> body){return service().patch(key(firstId,secondId),body);}
    @DeleteMapping("/{firstId}/{secondId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable UUID firstId,@PathVariable UUID secondId){service().delete(key(firstId,secondId));}
}
