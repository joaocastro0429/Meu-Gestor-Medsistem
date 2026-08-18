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
public class UserBranchId implements Serializable {
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "branch_id")
    private UUID branchId;
}
