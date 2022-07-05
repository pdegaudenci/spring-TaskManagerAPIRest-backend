package com.example.springbackendAPIREST.security.service;


import com.example.springbackendAPIREST.entidades.User;
import com.example.springbackendAPIREST.respositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Autentica un usuario de la base de datos
 *
 * Authentication Manager llama al método loadUserByUsername de esta clase
 * para obtener los detalles del usuario de la base de datos cuando
 * se intente autenticar un usuario
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email no encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),new ArrayList<>());
    }
}
