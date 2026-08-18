package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;
import jakarta.persistence.*; import lombok.*; import java.io.Serializable; import java.util.UUID;
@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class SaasPlanModuleId implements Serializable {
    @Column(name="plan_id") private UUID planId;
    @Column(name="module_id") private UUID moduleId;
}
