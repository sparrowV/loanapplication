package eu.twinno.loanapplication.core.models;

import eu.twinno.loanapplication.configuration.security.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@Entity
@Data
@Table(name = "loan_applications")
@NoArgsConstructor
public class LoanApplication {

    public enum RequestedTermType {
        DAY, MONTH
    }

    public enum Status {
        APPROVED, REJECTED, MANUAL
    }

    public enum SortingFields{
        FIRSTNAME("firstName","firstname"),LASTNAME("lastName","lastname");

        private String fieldValue;
        private String displayValue;

         SortingFields(String fieldValue,String displayValue){
            this.fieldValue = fieldValue;
            this.displayValue = displayValue;
        }

        public String getFieldValue(){
             return fieldValue;
        }

        public String getDisplayValue(){return displayValue;}

        public void setFieldValue(String fieldValue){
             this.fieldValue = fieldValue;
        }
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    @NotEmpty(message = "first name should not be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "last name should not be empty")
    private String lastName;

    @Column(name = "personal_id")
    @NotEmpty(message = "personal id should not be empty")
    @Size(min = 11,max = 11,message = "personal id size mus be 11")
    private String personalId;

    @Column(name = "birth_date")
    @NotNull(message = "birth date should not be empty")
    private LocalDate birthDate;

    @Column(name = "employer")
    private String employer;

    @Column(name = "salary")
    @NotNull(message = "salary should not be empty")
    private Double salary;

    @Column(name = "monthly_liability")
    @NotNull(message = "monthly liability should not be empty")
    private Double monthlyLiability;

    @Column(name = "requested_amount")
    @NotNull(message = "requested amount should not be empty")
    private Double requestedAmount;

    @Column(name = "requested_term")
    @NotNull(message = "requested term should not be empty")
    private Integer requestedTerm;

    @Column(name = "requested_term_type")
    @NotNull(message = "requested term type should not be empty")
    private RequestedTermType requestedTermType;

    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private User operator;

    @Column(name = "score")
    private Double score;


    private void scoreApplication() {
        score = 0.0;
        for (char ch : firstName.toCharArray()) {
            score += Character.toLowerCase(ch) - 'a' + 1;
        }
        score += 1.5 * this.salary;
        score -= monthlyLiability * 3;
        score += birthDate.getYear() - birthDate.getMonthValue();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.DAY_OF_MONTH, birthDate.getDayOfMonth());
        gc.set(GregorianCalendar.MONTH, birthDate.getMonthValue());
        gc.set(GregorianCalendar.YEAR, birthDate.getYear());
        score -= gc.get(GregorianCalendar.DAY_OF_YEAR);
    }

    @PrePersist
    public void prePersist() {
        scoreApplication();
        if (score < 2500) {
            status = Status.REJECTED;
        } else if (score > 3500) {
            status = Status.APPROVED;
        } else {
            status = Status.MANUAL;
        }
    }
}
