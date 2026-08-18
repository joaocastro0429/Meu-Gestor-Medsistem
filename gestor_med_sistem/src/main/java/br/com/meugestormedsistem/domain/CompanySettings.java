package br.com.meugestormedsistem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.*;

@Entity @Table(name="company_settings") @Getter @Setter @NoArgsConstructor
public class CompanySettings {
    @Id @NotNull @Column(name="company_id") private UUID companyId;
    @NotBlank @Size(max=60) private String timezone;
    @NotBlank @Size(max=20) private String locale;
    @NotBlank @Pattern(regexp="[A-Z]{3}") private String currency;
    @NotNull @JdbcTypeCode(SqlTypes.JSON) @Column(columnDefinition="jsonb") private Map<String, Object> settings = new HashMap<>();
}
