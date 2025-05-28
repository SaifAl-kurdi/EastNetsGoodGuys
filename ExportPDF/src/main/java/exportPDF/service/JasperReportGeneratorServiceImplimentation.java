package exportPDF.service;

import exportPDF.dtos.PDFGoodGuysDTO;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Map;

@RequestScoped
public class JasperReportGeneratorServiceImplimentation implements JasperReportGeneratorService{
    @Override
    public void generatePdfReport(JasperReport jasperReport, String outputFileName, Map<String, Object> parameters, List<PDFGoodGuysDTO> goodGuysDTOS) {
        try {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(goodGuysDTOS);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }
}