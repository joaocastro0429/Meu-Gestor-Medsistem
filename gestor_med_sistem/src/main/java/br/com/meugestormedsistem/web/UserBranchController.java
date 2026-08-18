package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.service.UserBranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/user-branches")
public class UserBranchController {
    private final UserBranchService service;

    public UserBranchController(UserBranchService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserBranch> listar() {
        return service.findAll();
    }

    @GetMapping("/{userId}/{branchId}")
    public UserBranch get(@PathVariable UUID userId, @PathVariable UUID branchId) {
        return service.findById(key(userId, branchId)).orElseThrow(this::notFound);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserBranch create(@Valid @RequestBody Request body) {
        UserBranch e = new UserBranch();
        e.setId(key(body.userId(), body.branchId()));
        return service.save(e);
    }

    @PutMapping("/{userId}/{branchId}")
    public UserBranch put(@PathVariable UUID userId, @PathVariable UUID branchId) {
        UserBranchId id = key(userId, branchId);
        if (!service.existsById(id))
            throw notFound();
        UserBranch e = new UserBranch();
        e.setId(id);
        return service.save(e);
    }

    @DeleteMapping("/{userId}/{branchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId, @PathVariable UUID branchId) {
        UserBranchId id = key(userId, branchId);
        if (!service.existsById(id))
            throw notFound();
        service.deleteById(id);
    }

    private UserBranchId key(UUID a, UUID b) {
        return new UserBranchId(a, b);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Vínculo não encontrado");
    }

    public record Request(@NotNull UUID userId, @NotNull UUID branchId) {
    }
}
