package importXML.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "XML")
public class XMLWrapperDTO {

    @XmlElement(name = "GOODGUYS")
    private GoodGuysDTO goodGuysDTO;

    public GoodGuysDTO getGoodGuysDTOOO() {
        return goodGuysDTO;
    }
    public void setGoodGuysDTOOOO(GoodGuysDTO goodGuysDTO) {
        this.goodGuysDTO = goodGuysDTO;
    }
}