package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.Authority;
import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.repositories.UserRepository;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.forEach(user->{
            user.getAuthorities().forEach(authority ->{
                if(authority.getAuthorityName().contains("OPERATOR")){
                    user.setOperator(true);
                }
            } );
        });
        return users;
    }

    @Override
    public Dto makeUserOperator(Integer userId, User currentUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            return Dto.Result.USER_DOES_NOT_EXIST.getResponse();
        }
        User user = optionalUser.get();
        if(user.getId().equals(currentUser.getId())){
            return Dto.Result.CHANGE_STATUS_TO_HIMSELF_ERROR.getResponse();
        }
        Authority roleOperator = authorityService.findByAuthorityNameAndActiveTrue("ROLE_OPERATOR");
        user.getAuthorities().add(roleOperator);
        return Dto.Result.USER_STATUS_CHANGE_SUCCESSFUL.getResponse(userRepository.save(user));
    }
}
