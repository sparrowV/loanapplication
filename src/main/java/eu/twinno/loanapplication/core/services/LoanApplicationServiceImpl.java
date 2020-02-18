package eu.twinno.loanapplication.core.services;

import eu.twinno.loanapplication.configuration.security.User;
import eu.twinno.loanapplication.core.models.LoanApplication;
import eu.twinno.loanapplication.core.repositories.LoanApplicationRepository;
import eu.twinno.loanapplication.core.utils.Dto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<LoanApplication> getAllApplications(String sortingDisplayName, Sort.Direction direction, LoanApplication.Status status) {
        if (sortingDisplayName != null && direction != null) {
            String fieldValue = Stream.of(LoanApplication.SortingFields.values())
                    .filter(value -> value.getDisplayValue().equals(sortingDisplayName))
                    .map(LoanApplication.SortingFields::getFieldValue)
                    .collect(Collectors.joining());
            if (!fieldValue.isEmpty()) {
                if(status != null){
                    return loanApplicationRepository.findByStatus(status,Sort.by(direction,fieldValue));
                }
                return loanApplicationRepository.findAll(Sort.by(direction,fieldValue));
            }
        }
        if(status != null) return loanApplicationRepository.findByStatus(status,null);
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

    @Override
    public Dto changeLoanApplicationStatus(Integer loanAppId,LoanApplication.Status newStatus, User operator) {
        Optional<LoanApplication> loanAppOptional = loanApplicationRepository.findById(loanAppId);
        if(!loanAppOptional.isPresent()){
            return Dto.Result.LOAN_APPLICATION_DOES_NOT_EXIST.getResponse();
        }
        LoanApplication loanApp = loanAppOptional.get();
        if(loanApp.getStatus() != LoanApplication.Status.MANUAL){
            Dto response = Dto.Result.LOAN_APPLICATION_STATUS_CHANGE_ERROR.getResponse();
            response.setDescription(String.format("loan application with id=%d does not have status MANUAL",loanApp.getId()));
            return response;
        }
        loanApp.setStatus(newStatus);
        loanApp.setOperator(operator);
        return Dto.Result.LOAN_APPLICATION_STATUS_CHANGE_SUCCESSFUL.getResponse(loanApplicationRepository.save(loanApp));
    }

    @Override
    public List<String> getSortingFields() {
        List<String> sortingDisplayNames = Stream.of(LoanApplication.SortingFields.values())
                .map(LoanApplication.SortingFields::getDisplayValue)
                .collect(Collectors.toList());
        return sortingDisplayNames;
    }

    private void validate(Integer id) {
        //check if applicaiton belongs to the current user
    }
}
