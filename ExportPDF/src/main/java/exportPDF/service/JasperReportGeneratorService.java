package exportPDF.service;

import exportPDF.dtos.PDFGoodGuysDTO;
import net.sf.jasperreports.engine.JasperReport;
import java.util.List;
import java.util.Map;

public interface JasperReportGeneratorService {
    void generatePdfReport(JasperReport jasperReport, String outputFileName, Map<String, Object> parameters, List<PDFGoodGuysDTO> goodGuysDTOS);
}
