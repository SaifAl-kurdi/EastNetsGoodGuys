package exportPDF.singleton;

import net.sf.jasperreports.engine.JasperReport;

public interface PDFTemplateManagerService {
    JasperReport getTemplate(String templatePath) throws Exception;
}
