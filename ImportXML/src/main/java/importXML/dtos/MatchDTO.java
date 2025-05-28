package importXML.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MATCH")
public class MatchDTO {

    @XmlElement(name = "ACCEPTED_MATCH")
    private String acceptedMatch;

    @XmlElement(name = "CHECKSUM")
    private String checkSum;

    @XmlElement(name = "ENTITY_MAIN_NAME")
    private String entityMainName;

    @XmlElement(name = "EXTERNAL_ID")
    private String externalID;

    @XmlElement(name = "LIST_NAME")
    private String listName;

    public String getAcceptedMatchCustom() {
        return acceptedMatch;
    }
    public void setAcceptedMatchCustom(String acceptedMatch) {
        this.acceptedMatch = acceptedMatch;
    }

    public String getCheckSumCustom() {
        return checkSum;
    }
    public void setCheckSumCustom(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getEntityMainNameCustom() {
        return entityMainName;
    }
    public void setEntityMainNameCustom(String entityMainName) {
        this.entityMainName = entityMainName;
    }

    public String getExternalIDCustom() {
        return externalID;
    }
    public void setExternalIDCustom(String externalID) {
        this.externalID = externalID;
    }

    public String getListNameCustom() {
        return listName;
    }
    public void setListNameCustom(String listName) {
        this.listName = listName;
    }

}