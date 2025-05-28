package importXML.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@XmlRootElement(name = "GOODGUYS")
public class GoodGuysDTO {

    @XmlElement(name = "GOODGUY")
    private List<GoodGuyDTO> goodGuy;

    @XmlTransient
    public List<GoodGuyDTO> getGoodGuyDTOList() {
        return goodGuy;
    }
    public void setGoodGuyDTOList(List<GoodGuyDTO> goodGuy) {
        this.goodGuy = goodGuy;
    }
}