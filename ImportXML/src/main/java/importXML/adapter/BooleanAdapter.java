package importXML.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {
    @Override
    public Boolean unmarshal(String value) {
        return "YES".equalsIgnoreCase(value);
    }

    @Override
    public String marshal(Boolean value) {
        return value != null && value ? "YES" : "NO";
    }
}
