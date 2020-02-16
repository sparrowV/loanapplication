package eu.twinno.loanapplication.core.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "loan_applications")
public class LoanApplication {

    public enum RequestedTermType{
        DAY,MONTH
    }

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name = "personal_id")
    private String personalId;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "employer")
    private String employer;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "monthly_liability")
    private BigDecimal monthlyLiability;

    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    @Column(name = "requested_term")
    private Integer requestedTerm;

    @Column(name = "requested_term_type")
    private RequestedTermType requestedTermType;


}
