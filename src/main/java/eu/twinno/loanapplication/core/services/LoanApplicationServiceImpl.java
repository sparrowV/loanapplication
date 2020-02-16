package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.repositories.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
