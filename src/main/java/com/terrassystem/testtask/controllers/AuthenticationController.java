package com.terrassystem.testtask.controllers;

import com.terrassystem.testtask.entity.User;
import com.terrassystem.testtask.security.TokenUtil;
import com.terrassystem.testtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by Павло on 21.06.2017.
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Void> authenticationRequest(@RequestParam("username") String name, @RequestParam("password") String password, HttpServletResponse response) throws AuthenticationException, UnsupportedEncodingException {
        User user = this.userService.getByUsernameAndPassword(name, password);
        if(user == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        String jwt = TokenUtil.generateJWT(name, password);
        response.addHeader("Authorization", "Basic " + jwt);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
