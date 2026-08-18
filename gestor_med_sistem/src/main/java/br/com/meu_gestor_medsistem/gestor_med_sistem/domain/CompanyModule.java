package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;
import jakarta.persistence.*; import jakarta.validation.Valid; import jakarta.validation.constraints.NotNull; import lombok.*;
@Entity @Table(name="company_module") @Getter @Setter @NoArgsConstructor
public class CompanyModule {
    @EmbeddedId @Valid @NotNull private CompanyModuleId id;
    private boolean enabled;
}
