package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Cnpj {
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos")
    private String value;
}
