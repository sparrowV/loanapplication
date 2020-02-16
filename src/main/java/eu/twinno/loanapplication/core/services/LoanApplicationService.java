package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.core.models.LoanApplication;

import java.util.List;

public interface LoanApplicationService {

    void create(LoanApplication loanApplication);

    List<LoanApplication> getAllApplications();

    void delete(Integer id);
}
