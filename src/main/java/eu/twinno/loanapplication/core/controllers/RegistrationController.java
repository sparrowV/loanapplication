package eu.twinno.loanapplication.core.controllers;
import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.services.UserService;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    @PostMapping
    public Dto register(User user){
        return userService.register(user);
    }


}
