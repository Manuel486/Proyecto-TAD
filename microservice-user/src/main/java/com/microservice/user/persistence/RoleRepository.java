package com.microservice.user.persistence;

import com.microservice.user.entities.Role;
import com.microservice.user.entities.RoleEnum;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByRoleEnum(RoleEnum roleEnum);
}
