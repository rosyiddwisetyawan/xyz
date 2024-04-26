package com.api.xzy.service;


import com.api.xzy.model.TblUser;
import com.api.xzy.model.UserRequest;
import com.api.xzy.repository.TblUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private TblUserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TblUser user = repository.findByUsernameAndStatus(username,1);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public TblUser save(UserRequest user) {
        TblUser newUser = new TblUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setMobilenumber(user.getMobilenumber());
        newUser.setStatus(1);
        return repository.save(newUser);
    }

    public Boolean checkUsername(UserRequest user){
        Boolean status = repository.existsByUsername(user.getUsername());
        return status;
    }

    public Boolean checkMobileNumber(UserRequest user){
        Boolean status = repository.existsByMobilenumber(user.getMobilenumber());
        return status;
    }

    public Boolean validationUsernameAndMobileNumber(UserRequest user){
        if(checkUsername(user) && checkMobileNumber(user)){
            return true;
        }
        return false;
    }

    public TblUser findByUsername(String username){
        TblUser user = repository.findByUsername(username);
        return user;
    }
}
