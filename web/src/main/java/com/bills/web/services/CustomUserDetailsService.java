package com.bills.web.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bills.web.entities.Users;
import com.bills.web.model.UserModel;
import com.bills.web.model.UserRole;
import com.bills.web.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel byLogin = getUser(username);
        if (byLogin == null) {
            return null;
        }
        return User.builder()
                .username(byLogin.getUsername())
                .password(byLogin.getPassword())
                .roles(byLogin.getRole().name())
                .build();
    }

    private UserModel getUser(String username){
        Optional<Users> optUser = usersRepository.findByUserName(username);
        if(optUser.isPresent()){
            Users user = optUser.get();
            return new UserModel(user.getUsername(),passwordEncoder.encode(user.getPassword()),UserRole.USER);
        }
        return null;
    }
    
}
