package search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "AcceptedMatches")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptedMatches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String checkSum;
    private String externalID;
    private String listName;
    private String acceptedMatch;
    private String entityMainName;
    @ManyToOne
    @JoinColumn(name = "goodguy_id", nullable = false)
    private GoodGuys goodGuy;
}