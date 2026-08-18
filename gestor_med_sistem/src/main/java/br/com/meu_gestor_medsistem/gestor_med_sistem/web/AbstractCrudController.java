package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.service.CrudService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

public abstract class AbstractCrudController<T> {
    protected abstract CrudService<T, UUID> service();

    @GetMapping public List<T> list() { return service().findAll(); }
    @GetMapping("/{id}") public T get(@PathVariable UUID id) { return service().findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public T create(@Valid @RequestBody T body) { return service().create(body); }
    @PutMapping("/{id}") public T replace(@PathVariable UUID id, @Valid @RequestBody T body) { return service().replace(id, body); }
    @PatchMapping("/{id}") public T patch(@PathVariable UUID id, @RequestBody Map<String, Object> changes) { return service().patch(id, changes); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable UUID id) { service().delete(id); }
}
