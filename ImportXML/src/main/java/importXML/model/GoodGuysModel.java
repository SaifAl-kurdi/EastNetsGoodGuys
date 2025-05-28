package importXML.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GoodGuys")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class GoodGuysModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String acceptedString;
    private String condition;
    private String comments;
    private LocalDateTime creationDate;
    private String createdBy;
    private boolean reportViolations;
    private boolean shared;
    private int rank;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "goodGuysModel")
    private List<AcceptedMatchesModel> acceptedMatchesModels;
}