package importXML.template;

import java.util.List;

public abstract class AbstractXMLImporter {
    public final void importXML(byte[] xmlData, String fileName, String token) {
        try {
            Object parsedData = parseXML(xmlData);
            validateData(parsedData);
            List<?> entities = mapToEntities(parsedData);
            saveToDatabase(entities, fileName, token);
        } catch (Exception e) {
            handleError(e);
        }
    }

    public void handleError(Exception e) {
        System.err.println("Error during XML import: " + e.getMessage());
    }

    protected abstract Object parseXML(byte[] xmlFile) throws Exception;

    protected abstract void validateData(Object parsedData) throws Exception;

    protected abstract List<?> mapToEntities(Object parsedData);

    protected abstract void saveToDatabase(List<?> entities, String fileName, String token);
}