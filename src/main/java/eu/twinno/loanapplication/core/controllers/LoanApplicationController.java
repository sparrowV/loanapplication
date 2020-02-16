package eu.twinno.loanapplication.core.controllers;

import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.services.LoanApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loanApplication")
@AllArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public void create(@RequestBody LoanApplication loanApplication){
        loanApplicationService.create(loanApplication);
    }

    @GetMapping
    public List<LoanApplication> getAll(){
        return loanApplicationService.getAllApplications();
    }




}
