package eu.twinno.loanapplication.configuration.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import eu.twinno.loanapplication.configuration.security.Authority;
import eu.twinno.loanapplication.core.models.LoanApplication;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "enabled")
    @ColumnDefault(value = "true")
    @JsonIgnore
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "user_authorities",
            joinColumns = { @JoinColumn(name = "user_id",referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id",referencedColumnName = "id") }
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Authority> authorities;

    @Transient
    private boolean isOperator=false;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "creator",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanApplication> loanApplications;

}
