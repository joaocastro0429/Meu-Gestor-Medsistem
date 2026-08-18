package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name = "saas_plan") @Getter @Setter @NoArgsConstructor
public class SaasPlan {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @NotBlank @Size(max = 120) private String name;
    @NotNull @DecimalMin("0.00") @Column(name = "default_price", precision = 15, scale = 2) private BigDecimal defaultPrice;
    @Positive @Column(name = "duration_days") private int durationDays;
    @NotNull @Enumerated(EnumType.STRING) private Status status;
}
