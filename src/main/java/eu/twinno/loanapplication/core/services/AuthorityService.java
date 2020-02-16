package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.Authority;

public interface AuthorityService {
    Authority findByAuthorityNameAndActiveTrue(String authorityName);
}
