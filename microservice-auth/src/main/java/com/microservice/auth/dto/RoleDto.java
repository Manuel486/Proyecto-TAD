package com.microservice.auth.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private RoleEnum roleEnum;
    private Set<PermissionDto> permissionList;
}
