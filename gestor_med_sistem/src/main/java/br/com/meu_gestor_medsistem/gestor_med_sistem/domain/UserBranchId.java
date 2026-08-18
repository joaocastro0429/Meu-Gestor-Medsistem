package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;
import jakarta.persistence.*; import lombok.*; import java.io.Serializable; import java.util.UUID;
@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class UserBranchId implements Serializable {
    @Column(name="user_id") private UUID userId;
    @Column(name="branch_id") private UUID branchId;
}
