package eu.twinno.loanapplication.core.controllers;

import eu.twinno.loanapplication.configuration.security.MyUserDetails;
import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.services.UserService;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PutMapping("/{id}/status")
    public Dto makeUserOperator(@PathVariable Integer id, Principal principal){
        return userService.makeUserOperator(id,((MyUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser());
    }



}
