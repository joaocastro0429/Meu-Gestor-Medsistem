package br.com.meugestormedsistem.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Column(name = "company_id")
    private UUID companyId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;
    @NotBlank
    @Size(max = 120)
    private String name;
    @Valid
    @NotNull
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private Email email;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
