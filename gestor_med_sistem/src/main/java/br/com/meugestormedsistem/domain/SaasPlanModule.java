package br.com.meugestormedsistem.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "saas_plan_module")
@Getter
@Setter
@NoArgsConstructor
public class SaasPlanModule {
    @EmbeddedId
    @Valid
    @NotNull
    private SaasPlanModuleId id;
}
