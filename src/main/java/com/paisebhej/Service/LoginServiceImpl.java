package com.paisebhej.Service;

import com.paisebhej.DTO.LoginDTO;
import com.paisebhej.Exceptions.LoginException;
import com.paisebhej.Model.CurrentUserSession;
import com.paisebhej.Model.Customer;
import com.paisebhej.Respository.CustomerRepository;
import com.paisebhej.Respository.SessionRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public String logIntoAccount(LoginDTO loginDTO) throws LoginException {
        Customer existingCustomer = customerRepository.findByMobileNumber(loginDTO.getMobileNumber());
        if (existingCustomer==null){
            throw  new LoginException("PLEASE ENTER A VALID MOBILE NUMBER");
        }
      Optional<CurrentUserSession> currentUserSession= sessionRepository.findById(existingCustomer.getCustomerId());
        if (existingCustomer.getPassword().equals(loginDTO.getPassword())){
            String key = RandomString.make(6);
            CurrentUserSession currentUserSession1 = new CurrentUserSession(existingCustomer.getCustomerId(),key, LocalDateTime.now());
            sessionRepository.save(currentUserSession1);
            return currentUserSession1.toString();
        }else
            throw new LoginException("PLEASE ENTER A VALID PASSWORD");

    }

    @Override
    public String checkLogin(String key) throws LoginException {
        CurrentUserSession currentUserSession = sessionRepository.findByUuid(key);
        if (currentUserSession==null){
            throw new LoginException("USER NOT LOGGED IN");
        }
        return "USER LOGGED IN";
    }

    @Override
    public String logOutFromTheAccount(String key) throws LoginException {
        CurrentUserSession currentUserSession = sessionRepository.findByUuid(key);
        if (currentUserSession==null){
            throw new LoginException("USER NOT LOGGED IN WITH THIS NUMBER");
        }
        sessionRepository.delete(currentUserSession);
        return "USER SUCCESSFULLY LOGGED OUT";
    }
}
