package br.com.meugestormedsistem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity @Table(name = "company_subscription") @Getter @Setter @NoArgsConstructor
public class CompanySubscription {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @NotNull @Column(name = "company_id") private UUID companyId;
    @NotNull @Column(name = "plan_id") private UUID planId;
    @NotNull @DecimalMin("0.00") @Column(precision = 15, scale = 2) private BigDecimal price;
    @NotNull @Column(name = "starts_on") private LocalDate startsOn;
    @NotNull @Column(name = "expires_on") private LocalDate expiresOn;
    @NotNull @Enumerated(EnumType.STRING) private SubscriptionStatus status;
    @AssertTrue(message = "expiresOn deve ser igual ou posterior a startsOn")
    public boolean isPeriodValid() { return startsOn == null || expiresOn == null || !expiresOn.isBefore(startsOn); }
}
