package eu.twinno.loanapplication.core.repositories;

import eu.twinno.loanapplication.configuration.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String username);
}
