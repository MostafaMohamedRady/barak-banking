package com.barka.application.customer.service;

import com.barka.application.customer.dto.AuthRequest;
import com.barka.application.customer.dto.AuthResponse;
import com.barka.application.customer.entity.Customer;
import com.barka.application.exception.AccessException;
import com.barka.application.secuirty.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(),
                new ArrayList<>());
    }

    public AuthResponse authorize(AuthRequest request) {
        UserDetails userDetails = loadUserByUsername(request.getEmail());
        authenticate(request.getEmail(), request.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info("Success logged user {}", userDetails.getUsername());
        return AuthResponse.builder().token(token).build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AccessException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new AccessException("INVALID_CREDENTIALS");
        }
    }
}
