package eu.twinno.loanapplication.core.repositories;

import eu.twinno.loanapplication.core.models.LoanApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Integer> {
    List<LoanApplication> findByStatus(LoanApplication.Status status, Sort sort);
}
