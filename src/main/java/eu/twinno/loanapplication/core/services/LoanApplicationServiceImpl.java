package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.repositories.LoanApplicationRepository;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;

    @Override
    public Dto create(LoanApplication loanApplication, BindingResult bindingResult,User creator) {
        if(bindingResult.hasErrors()){
            return Dto.Result.LOAN_APPLICATION_VALIDATION_FAILED.getResponse(bindingResult.getAllErrors());
        }
        loanApplication.setCreator(creator);
        return Dto.Result.LOAN_APPLICATION_REGISTRATION_SUCCESSFUL.getResponse(loanApplicationRepository.save(loanApplication));
    }

    @Override
    public List<LoanApplication> getAllApplications() {
        return loanApplicationRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        validate(id);
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(id);
        if(loanApplication.isPresent()){
            loanApplicationRepository.delete(loanApplication.get());
        }
    }

    private void validate(Integer id) {

    }
}
