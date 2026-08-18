package br.com.meugestormedsistem.web;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.service.UserPermissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/user-permissions")
public class UserPermissionController {
    private final UserPermissionService service;

    public UserPermissionController(UserPermissionService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserPermission> listar() {
        return service.findAll();
    }

    @GetMapping("/{userId}/{moduleId}")
    public UserPermission get(@PathVariable UUID userId, @PathVariable UUID moduleId) {
        return service.findById(key(userId, moduleId)).orElseThrow(this::notFound);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserPermission create(@Valid @RequestBody Request body) {
        UserPermission e = new UserPermission();
        e.setId(key(body.userId(), body.moduleId()));
        copy(body, e);
        return service.save(e);
    }

    @PutMapping("/{userId}/{moduleId}")
    public UserPermission put(@PathVariable UUID userId, @PathVariable UUID moduleId,
            @Valid @RequestBody Request body) {
        UserPermissionId id = key(userId, moduleId);
        if (!service.existsById(id))
            throw notFound();
        UserPermission e = new UserPermission();
        e.setId(id);
        copy(body, e);
        return service.save(e);
    }

    @PatchMapping("/{userId}/{moduleId}")
    public UserPermission patch(@PathVariable UUID userId, @PathVariable UUID moduleId,
            @RequestBody Map<String, Boolean> b) {
        UserPermission e = get(userId, moduleId);
        if (b.containsKey("canView"))
            e.setCanView(b.get("canView"));
        if (b.containsKey("canCreate"))
            e.setCanCreate(b.get("canCreate"));
        if (b.containsKey("canEdit"))
            e.setCanEdit(b.get("canEdit"));
        if (b.containsKey("canDelete"))
            e.setCanDelete(b.get("canDelete"));
        if (b.containsKey("canApprove"))
            e.setCanApprove(b.get("canApprove"));
        if (b.containsKey("canManage"))
            e.setCanManage(b.get("canManage"));
        return service.save(e);
    }

    @DeleteMapping("/{userId}/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId, @PathVariable UUID moduleId) {
        UserPermissionId id = key(userId, moduleId);
        if (!service.existsById(id))
            throw notFound();
        service.deleteById(id);
    }

    private void copy(Request b, UserPermission e) {
        e.setCanView(b.canView());
        e.setCanCreate(b.canCreate());
        e.setCanEdit(b.canEdit());
        e.setCanDelete(b.canDelete());
        e.setCanApprove(b.canApprove());
        e.setCanManage(b.canManage());
    }

    private UserPermissionId key(UUID a, UUID b) {
        return new UserPermissionId(a, b);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Permissão não encontrada");
    }

    public record Request(@NotNull UUID userId, @NotNull UUID moduleId, boolean canView, boolean canCreate,
            boolean canEdit, boolean canDelete, boolean canApprove, boolean canManage) {
    }
}
