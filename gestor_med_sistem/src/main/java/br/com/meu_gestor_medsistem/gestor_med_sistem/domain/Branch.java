package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "branch") @Getter @Setter @NoArgsConstructor
public class Branch {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @NotNull @Column(name = "company_id") private UUID companyId;
    @NotBlank @Size(max = 60) private String code;
    @NotBlank @Size(max = 120) private String name;
    private boolean active;
}
