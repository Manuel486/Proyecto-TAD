package com.microservice.auth.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private String name;
}
