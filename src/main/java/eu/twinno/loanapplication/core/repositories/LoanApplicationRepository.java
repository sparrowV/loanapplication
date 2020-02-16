package eu.twinno.loanapplication.core.repositories;

import eu.twinno.loanapplication.core.models.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Integer> {
}
