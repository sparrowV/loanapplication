package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.Authority;
import eu.twinno.loanapplication.core.repositories.AuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService{
    private final AuthorityRepository authorityRepository;


    @Override
    public Authority findByAuthorityNameAndActiveTrue(String authorityName) {
        return authorityRepository.findByAuthorityNameAndActiveTrue(authorityName);
    }
}
