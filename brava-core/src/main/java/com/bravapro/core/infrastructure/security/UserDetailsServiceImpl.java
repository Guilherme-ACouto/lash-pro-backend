package com.bravapro.core.infrastructure.security;

import com.bravapro.core.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userJpaRepository
                .findByEmail(email)
                .map(entity -> new AuthenticatedUser(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getName(),
                        entity.getPassword(),
                        entity.isAdmin(),
                        entity.isActive(),
                        entity.getTenantId(),
                        entity.getTenantId(),
                        entity.getTokenVersion()))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}
