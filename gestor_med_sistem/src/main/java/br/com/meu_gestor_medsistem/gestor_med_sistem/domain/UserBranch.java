package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;
import jakarta.persistence.*; import jakarta.validation.Valid; import jakarta.validation.constraints.NotNull; import lombok.*;
@Entity @Table(name="user_branch") @Getter @Setter @NoArgsConstructor
public class UserBranch {
    @EmbeddedId @Valid @NotNull private UserBranchId id;
}
