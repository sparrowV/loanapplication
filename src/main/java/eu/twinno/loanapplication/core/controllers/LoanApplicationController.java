package eu.twinno.loanapplication.core.controllers;

import eu.twinno.loanapplication.configuration.security.MyUserDetails;
import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.services.LoanApplicationService;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/loanApplication")
@AllArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public Dto create(@Valid  @RequestBody LoanApplication loanApplication, BindingResult bindingResult, Principal principal){

        return loanApplicationService.create(loanApplication,bindingResult,((MyUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser());

    }

    @GetMapping
    public List<LoanApplication> getAll(){
        return loanApplicationService.getAllApplications();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        loanApplicationService.delete(id);
    }




}
