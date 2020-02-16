package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.Authority;
import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.repositories.UserRepository;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Dto register(User user) {
        User existingUser = findUserByUsername(user.getUserName());
        if(existingUser != null){
            return Dto.Result.USERNAME_TAKEN.getResponse();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(new ArrayList<Authority>(){
            {
                add(authorityService.findByAuthorityNameAndActiveTrue("ROLE_CLIENT"));

            }
        });
        user.setEnabled(true);
        return Dto.Result.REGISTRATION_SUCCESSFUL.getResponse(userRepository.save(user));
    }
}
