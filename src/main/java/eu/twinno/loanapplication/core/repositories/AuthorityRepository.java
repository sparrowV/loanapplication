package eu.twinno.loanapplication.core.repositories;

import eu.twinno.loanapplication.configuration.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
    Authority findByAuthorityNameAndActiveTrue(String authorityName);
}
