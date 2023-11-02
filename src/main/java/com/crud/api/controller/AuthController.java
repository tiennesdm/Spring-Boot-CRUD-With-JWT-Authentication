package com.crud.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.api.entity.AuthModel;
import com.crud.api.entity.UserModel;
import com.crud.api.response.ErrorObject;
import com.crud.api.response.SuccessObject;
import com.crud.api.security.CustonUserDetailService;
import com.crud.api.service.UserService;
import com.crud.api.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustonUserDetailService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthModel authModel) throws Exception {

        try {
            authenticate(authModel.getEmail(), authModel.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
            final Object token = jwtTokenUtil.generateToken(userDetails);
            SuccessObject successObject = new SuccessObject(200, token, "User Validated");
            return new ResponseEntity<>(successObject, HttpStatus.OK);

        } catch (Exception error) {
            ErrorObject errorObject = new ErrorObject(409, "Bad Request", null, error);
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        }

    }

    private ResponseEntity<ErrorObject> authenticate(String email, String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            ErrorObject errorObject = new ErrorObject(409, "User disabled", null, null);
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            ErrorObject errorObject = new ErrorObject(409, "Bad Credentials", null, null);
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        }
        return null;

    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserModel user) {
        try {
            if (Boolean.TRUE.equals(userService.checkUserExist(user))) {
                ErrorObject errorObject = new ErrorObject(409, "User Already Exist", null, null);
                return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
            } else {
                userService.createUser(user);
                authenticate(user.getEmail(), user.getPassword());
                final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                final Object token = jwtTokenUtil.generateToken(userDetails);
                SuccessObject successObject = new SuccessObject(200, token, "User Validated");
                return new ResponseEntity<>(successObject, HttpStatus.OK);
            }
        } catch (Exception error) {
            ErrorObject errorObject = new ErrorObject(409, "Bad Request", null, error);
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);

        }

    }

}
