package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.repositories.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;

    @Override
    public void create(LoanApplication loanApplication) {
        loanApplicationRepository.save(loanApplication);
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
