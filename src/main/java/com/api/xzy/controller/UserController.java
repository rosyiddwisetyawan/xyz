package com.api.xzy.controller;

import com.api.xzy.model.JwtRequest;
import com.api.xzy.model.JwtResponse;
import com.api.xzy.model.TblUser;
import com.api.xzy.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.api.xzy.service.JwtService;
import com.api.xzy.service.UserInfoService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());


        if (authentication.isAuthenticated() && userDetails!=null) {
            final String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserRequest user) throws Exception {
        Boolean statusChecking = userDetailsService.validationUsernameAndMobileNumber(user);
        Map<String, Object> map = new HashMap<String, Object>();
        if(statusChecking){

            map.put("message", "username already exist");
            map.put("username", user.getUsername());

        }else {
            TblUser dataUser = userDetailsService.save(user);
            if(dataUser!=null){
                map.put("message", "register success");
                map.put("username", user.getUsername());
            }else {
                map.put("message", "register failed");
                map.put("username", user.getUsername());
            }
        }
        return ResponseEntity.ok(map);
    }
}
