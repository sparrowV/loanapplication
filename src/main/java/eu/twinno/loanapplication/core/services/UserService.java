package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.utils.Dto;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    Dto register(User user);

    List<User> getAll();

    Dto makeUserOperator(Integer userId,User currentUser);
}
