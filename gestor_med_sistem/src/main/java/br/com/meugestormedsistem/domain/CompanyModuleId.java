package br.com.meugestormedsistem.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyModuleId implements Serializable {
    @Column(name = "company_id")
    private UUID companyId;
    @Column(name = "module_id")
    private UUID moduleId;
}
