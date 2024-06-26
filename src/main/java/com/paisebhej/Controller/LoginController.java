package com.paisebhej.Controller;

import com.paisebhej.DTO.LoginDTO;
import com.paisebhej.Exceptions.LoginException;
import com.paisebhej.Service.CustomerService;
import com.paisebhej.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<String> logInCustomer(@RequestBody LoginDTO loginDTO) throws LoginException {
        String result = loginService.logIntoAccount(loginDTO);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<String> checkLogin(@RequestParam(required = false) String key) throws LoginException {
        String result = loginService.checkLogin(key);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logoutCustomer(@RequestParam(required = false) String key) throws LoginException {
        return loginService.logOutFromTheAccount(key);
    }
}
