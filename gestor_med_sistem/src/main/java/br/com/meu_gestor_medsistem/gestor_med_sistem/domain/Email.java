package br.com.meu_gestor_medsistem.gestor_med_sistem.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Email {
    @NotBlank @jakarta.validation.constraints.Email
    private String value;
}
