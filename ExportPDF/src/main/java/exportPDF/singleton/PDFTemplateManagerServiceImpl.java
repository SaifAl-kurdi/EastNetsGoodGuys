package exportPDF.singleton;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import java.util.HashMap;
import java.util.Map;

public class PDFTemplateManagerServiceImpl implements PDFTemplateManagerService {

    private static PDFTemplateManagerServiceImpl instance;
    private final Map<String, JasperReport> templates;

    private PDFTemplateManagerServiceImpl() {
        templates = new HashMap<>();
    }
    
    public static PDFTemplateManagerServiceImpl getInstance() {
        if (instance == null) {
            instance = new PDFTemplateManagerServiceImpl();
        }
        return instance;
    }

    @Override
    public JasperReport getTemplate(String templatePath) throws Exception {
        if (!templates.containsKey(templatePath)) {
            JasperReport report = JasperCompileManager.compileReport(templatePath);
            templates.put(templatePath, report);
        }
        return templates.get(templatePath);
    }
}
