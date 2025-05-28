package search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GoodGuys")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodGuys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String acceptedString;
    private String condition;
    private String comments;
    private String creationDate;
    private String createdBy;
    private String reportViolations;
    private String shared;
    private int rank;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "goodGuy")
    private List<AcceptedMatches> acceptedMatches;
}