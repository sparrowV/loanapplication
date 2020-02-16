package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.utils.Dto;

public interface UserService {
    User findUserByUsername(String username);

    Dto register(User user);

}
