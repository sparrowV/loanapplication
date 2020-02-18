package eu.twinno.loanapplication.core.controllers;

import eu.twinno.loanapplication.configuration.security.MyUserDetails;
import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.services.LoanApplicationService;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/loanApplication")
@AllArgsConstructor
@PreAuthorize("hasRole('CLIENT')")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public Dto create(@Valid  @RequestBody LoanApplication loanApplication, BindingResult bindingResult, Principal principal){

        return loanApplicationService.create(loanApplication,bindingResult,((MyUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser());

    }

    @GetMapping
    public List<LoanApplication> getAll(@RequestParam(required = false) String sortingDisplayName
                                        ,@RequestParam(required = false) Sort.Direction direction,
                                        @RequestParam(required = false) LoanApplication.Status status){
        return loanApplicationService.getAllApplications(sortingDisplayName,direction,status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        loanApplicationService.delete(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('OPERATOR')")
    public Dto changeStatus(@PathVariable(name = "id") Integer loanApplicationId,@RequestParam LoanApplication.Status newStatus,Principal principal){
        return loanApplicationService.changeLoanApplicationStatus(loanApplicationId,newStatus,((MyUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser());
    }


    @GetMapping("/sortingFields")
    public List<String> getSortingFields(){
        return loanApplicationService.getSortingFields();
    }






}
