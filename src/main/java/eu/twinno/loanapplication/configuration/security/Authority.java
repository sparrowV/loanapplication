package eu.twinno.loanapplication.configuration.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "authorities")
@JsonIgnoreType
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "authority_name")
    private String authorityName;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "authorities")
    @JsonBackReference
    private List<User> users;
}
