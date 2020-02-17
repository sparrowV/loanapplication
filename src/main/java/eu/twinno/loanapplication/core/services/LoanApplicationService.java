package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.utils.Dto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface LoanApplicationService {

    Dto create(LoanApplication loanApplication, BindingResult bindingResult, User creator);

    List<LoanApplication> getAllApplications();

    void delete(Integer id);
}
