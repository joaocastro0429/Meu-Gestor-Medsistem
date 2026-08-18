package br.com.meu_gestor_medsistem.gestor_med_sistem.service;

import tools.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.function.BiConsumer;

public class CrudService<T, ID> {
    private final JpaRepository<T, ID> repository;
    private final ObjectMapper mapper;
    private final Validator validator;
    private final BiConsumer<T, ID> idSetter;

    public CrudService(JpaRepository<T, ID> repository, ObjectMapper mapper, Validator validator,
                       BiConsumer<T, ID> idSetter) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.idSetter = idSetter;
    }

    public List<T> findAll() { return repository.findAll(); }
    public T findById(ID id) { return repository.findById(id).orElseThrow(this::notFound); }
    @Transactional public T create(T entity) { return repository.save(entity); }

    @Transactional public T replace(ID id, T entity) {
        if (!repository.existsById(id)) throw notFound();
        idSetter.accept(entity, id);
        validate(entity);
        return repository.save(entity);
    }

    @Transactional public T patch(ID id, Map<String, Object> changes) {
        if (changes.containsKey("id") || changes.containsKey("companyId"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O identificador não pode ser alterado");
        T entity = findById(id);
        try { mapper.updateValue(entity, changes); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PATCH inválido", ex); }
        validate(entity);
        return repository.save(entity);
    }

    @Transactional public void delete(ID id) {
        if (!repository.existsById(id)) throw notFound();
        repository.deleteById(id);
    }

    private void validate(T entity) {
        var violations = validator.validate(entity);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
    private ResponseStatusException notFound() { return new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado"); }
}
