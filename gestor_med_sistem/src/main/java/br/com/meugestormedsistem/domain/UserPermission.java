package br.com.meugestormedsistem.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "user_permission")
@Getter
@Setter
@NoArgsConstructor
public class UserPermission {
    @EmbeddedId
    @Valid
    @NotNull
    private UserPermissionId id;
    @Column(name = "can_view")
    private boolean canView;
    @Column(name = "can_create")
    private boolean canCreate;
    @Column(name = "can_edit")
    private boolean canEdit;
    @Column(name = "can_delete")
    private boolean canDelete;
    @Column(name = "can_approve")
    private boolean canApprove;
    @Column(name = "can_manage")
    private boolean canManage;
}
