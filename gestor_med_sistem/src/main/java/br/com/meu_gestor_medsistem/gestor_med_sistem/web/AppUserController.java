package br.com.meu_gestor_medsistem.gestor_med_sistem.web;

import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.AppUser;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.Email;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.UserStatus;
import br.com.meu_gestor_medsistem.gestor_med_sistem.domain.UserType;
import br.com.meu_gestor_medsistem.gestor_med_sistem.repository.AppUserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    private final AppUserRepository repository;

    public AppUserController(AppUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AppUser> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public AppUser buscar(@PathVariable UUID id) {
        return encontrar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser criar(@Valid @RequestBody Request body) {
        return repository.save(preencher(new AppUser(), body));
    }

    @PutMapping("/{id}")
    public AppUser atualizar(@PathVariable UUID id, @Valid @RequestBody Request body) {
        return repository.save(preencher(encontrar(id), body));
    }

    @PatchMapping("/{id}/status")
    public AppUser alterarStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest body) {
        AppUser u = encontrar(id);
        u.setStatus(body.status());
        return repository.save(u);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        repository.delete(encontrar(id));
    }

    private AppUser encontrar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    private AppUser preencher(AppUser u, Request r) {
        u.setCompanyId(r.companyId());
        u.setType(r.type());
        u.setName(r.name());
        u.setEmail(new Email(r.email()));
        u.setStatus(r.status());
        return u;
    }

    public record Request(@NotNull UUID companyId, @NotNull UserType type, @NotBlank String name,
            @NotBlank @jakarta.validation.constraints.Email String email,
            @NotNull UserStatus status) {
    }

    public record StatusRequest(@NotNull UserStatus status) {
    }
}
