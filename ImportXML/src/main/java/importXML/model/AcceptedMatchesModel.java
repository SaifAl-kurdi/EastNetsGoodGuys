package importXML.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "AcceptedMatches")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptedMatchesModel {

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
    private GoodGuysModel goodGuysModel;
}