package com.microservice.user;

import com.microservice.user.entities.Permission;
import com.microservice.user.entities.Role;
import com.microservice.user.entities.RoleEnum;
import com.microservice.user.entities.Usuario;
import com.microservice.user.persistence.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUsuariosApplication.class, args);
	}

	@Bean
	CommandLineRunner init_(UsuarioRepository usuarioRepository){
		return args -> {
			/* Create PERMISSIONS */
			Permission createPermission = Permission.builder()
					.name("CREATE")
					.build();

			Permission readPermission = Permission.builder()
					.name("READ")
					.build();

			Permission updatePermission = Permission.builder()
					.name("UPDATE")
					.build();

			Permission deletePermission = Permission.builder()
					.name("DELETE")
					.build();

			Permission refactorPermission = Permission.builder()
					.name("REFACTOR")
					.build();

			/* Create ROLES */
			Role roleAdmin = Role.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			Role roleTeacher = Role.builder()
					.roleEnum(RoleEnum.TEACHER)
					.permissionList(Set.of(readPermission, updatePermission, deletePermission))
					.build();

			Role roleUser = Role.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission))
					.build();


			/* CREATE USERS */
			Usuario userSantiago = Usuario.builder()
					.nombre("Santiago López")
					.codigo("19200050")
					.email("santiagolopez@gmail.com")
					.username("santiago")
					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
					.fotoPerfil("santiago.jpg")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			Usuario userDaniel = Usuario.builder()
					.nombre("Daniel Pérez")
					.codigo("19200051")
					.email("danielperez@gmail.com")
					.username("daniel")
					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
					.fotoPerfil("daniel.jpg")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();

			Usuario userAndrea = Usuario.builder()
					.nombre("Andrea Martínez")
					.codigo("19200053")
					.email("andreamartinez@gmail.com")
					.username("andrea")
					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
					.fotoPerfil("andrea.jpg")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleTeacher))
					.build();


			usuarioRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea));
		};
	}
}
