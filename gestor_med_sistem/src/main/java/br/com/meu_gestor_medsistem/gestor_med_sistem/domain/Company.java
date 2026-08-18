package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "company") @Getter @Setter @NoArgsConstructor
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @NotBlank @Size(max = 180) @Column(name = "legal_name") private String legalName;
    @NotBlank @Size(max = 180) @Column(name = "trade_name") private String tradeName;
    @Valid @NotNull @Embedded @AttributeOverride(name = "value", column = @Column(name = "cnpj")) private Cnpj cnpj;
    @NotNull @Enumerated(EnumType.STRING) private Status status;
}
