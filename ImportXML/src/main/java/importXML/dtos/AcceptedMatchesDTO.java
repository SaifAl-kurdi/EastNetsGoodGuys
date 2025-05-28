package importXML.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ACCEPTED_MATCHES")
public class AcceptedMatchesDTO {
    @XmlElement(name = "MATCH")
    private List<MatchDTO> matches;

    public List<MatchDTO> getMatchesCustom() {
        return matches;
    }
    public void setMatchesCustom(List<MatchDTO> matches) {
        this.matches = matches;
    }
}