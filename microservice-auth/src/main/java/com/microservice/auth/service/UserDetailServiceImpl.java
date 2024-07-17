package com.microservice.auth.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.auth.client.UserFeignClient;
import com.microservice.auth.controller.dto.AuthLoginRequest;
import com.microservice.auth.controller.dto.AuthResponse;
import com.microservice.auth.controller.dto.TokenDto;
import com.microservice.auth.dto.UserDto;
import com.microservice.auth.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto userDto = userFeignClient.obtenerUsuarioPorUsername(username).getBody();

        if ( userDto == null ) {
            throw new UsernameNotFoundException("No existe el usuario " + username);
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userDto.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userDto.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userDto.getUsername(), userDto.getPassword(), userDto.isEnabled(), userDto.isAccountNoExpired(), userDto.isCredentialNoExpired(), userDto.isAccountNoLocked(), authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponse(username, "User loged successfully", accessToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    public TokenDto validate(String token) {
        try {
            DecodedJWT decodedJWT = jwtUtils.validateToken(token);
            String username = jwtUtils.extractUsername(decodedJWT);
            if( userFeignClient.obtenerUsuarioPorUsername(username).getBody() == null) {
                return null;
            }
            return new TokenDto(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

}
