package com.paisebhej.Service;

import com.paisebhej.DTO.LoginDTO;
import com.paisebhej.Exceptions.LoginException;

public interface LoginService {
    public String logIntoAccount(LoginDTO loginDTO) throws LoginException;
    public String checkLogin(String key) throws LoginException;
    public String logOutFromTheAccount(String key) throws LoginException;
}
