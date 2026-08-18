package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "saas_module") @Getter @Setter @NoArgsConstructor
public class SaasModule {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @NotBlank @Size(max = 60) private String code;
    @NotBlank @Size(max = 120) private String name;
    @NotNull @Enumerated(EnumType.STRING) private Status status;
}
