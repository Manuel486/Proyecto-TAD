package com.microservice.user.service;

import com.microservice.user.dto.UsuarioDto;
import com.microservice.user.entities.Role;
import com.microservice.user.entities.RoleEnum;
import com.microservice.user.entities.Usuario;
import com.microservice.user.exception.ResourceNotFoundException;
import com.microservice.user.persistence.RoleRepository;
import com.microservice.user.persistence.UsuarioRepository;
import com.microservice.user.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final RoleRepository roleRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UsuarioDto> listAll() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        return StreamSupport.stream(usuarios.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Usuario save(UsuarioDto usuarioDto) {

        String encodedPassword = PasswordUtils.encodePassword(usuarioDto.getPassword());

        Role roleUser = roleRepository.findByRoleEnum(RoleEnum.USER);
        if (roleUser == null) {
            throw new RuntimeException("El rol 'USER' no está presente en la base de datos.");
        }

        Usuario usuario = Usuario.builder()
                .id(usuarioDto.getId())
                .nombre(usuarioDto.getNombre())
                .codigo(usuarioDto.getCodigo())
                .email(usuarioDto.getEmail())
                .username(usuarioDto.getUsername())
                .password(encodedPassword)
                .fotoPerfil(usuarioDto.getFotoPerfil())
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleUser))
                .build();
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDto findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        return convertToDto(usuario);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id){
        return usuarioRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public Usuario getUsuarioByUsername(String username){
        return usuarioRepository.findUsuarioByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Usuario no encontrado con el id: " + username));
    }


    private UsuarioDto convertToDto(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .codigo(usuario.getCodigo())
                .email(usuario.getEmail())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .fotoPerfil(usuario.getFotoPerfil())
                .build();
    }

}
