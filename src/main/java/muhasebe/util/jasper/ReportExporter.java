package muhasebe.util.jasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import muhasebe.util.exception.MUHException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Component
public class ReportExporter {

	private JasperPrint jasperPrint;

	public ReportExporter() {
	}

	public ReportExporter(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public byte[] exportToPdf(String fileName, String author) throws MUHException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// print report to file
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor(author);
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");
			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);

			exporter.exportReport();
		} catch (JRException ex) {
			ex.printStackTrace();
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return baos.toByteArray();
	}

	public byte[] exportToXlsx(String fileName, String sheetName) throws MUHException {
		byte[] rawBytes = null;
		try {
			JRXlsxExporter exporter = new JRXlsxExporter();
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			jasperPrint.setProperty(net.sf.jasperreports.engine.JRParameter.IS_IGNORE_PAGINATION, "true");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
			SimpleXlsxReportConfiguration xlsxreportConfig = new SimpleXlsxReportConfiguration();
			xlsxreportConfig.setSheetNames(new String[] { fileName + " Raporu" });
			xlsxreportConfig.setRemoveEmptySpaceBetweenRows(true);
			// xlsxreportConfig.setForcePageBreaks(false);
			xlsxreportConfig.setWrapText(false);
			xlsxreportConfig.setCollapseRowSpan(true);
			// xlsxreportConfig.setOnePagePerSheet(true);
			xlsxreportConfig.setDetectCellType(true);
			exporter.setConfiguration(xlsxreportConfig);
			exporter.exportReport();
			rawBytes = xlsReport.toByteArray();
		} catch (JRException ex) {
			ex.printStackTrace();
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rawBytes;
	}

	public byte[] exportToDocx(String fileName, String sheetName) throws MUHException {
		byte[] rawBytes = null;
		try {
			JRDocxExporter exporter = new JRDocxExporter();
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			jasperPrint.setProperty(net.sf.jasperreports.engine.JRParameter.IS_IGNORE_PAGINATION, "true");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
			exporter.exportReport();
			rawBytes = xlsReport.toByteArray();
		} catch (JRException ex) {
			ex.printStackTrace();
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rawBytes;
	}

	public void exportToCsv(String fileName) {
		JRCsvExporter exporter = new JRCsvExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(fileName));

		try {
			exporter.exportReport();
		} catch (JRException ex) {
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void exportToHtml(String fileName) {
		HtmlExporter exporter = new HtmlExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(fileName));

		try {
			exporter.exportReport();
		} catch (JRException ex) {
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// ======Eski metotlar

	public byte[] exportReportPdf(String raporAdi, Collection<?> dataSource, Map<String, Object> parametre)
			throws Exception {
		// String path = "src\\main\\resources\\" + raporAdi + ".jrxml";
		// String path = "src\\main\\java\\enerji\\util\\rapor\\" + raporAdi + ".jrxml";
		String path = "/BOOT-INF/classes/rapor/" + raporAdi + ".jrxml";
		File file = ResourceUtils.getFile(path);

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		Map<String, Object> parameters = parametre;
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
		byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=personel.pdf");
		return data;
	}

	public byte[] exportReportDocx(String rapor, Collection<?> list, Map<String, Object> parametre) throws Exception {
		String path = "src\\main\\java\\enerji\\util\\rapor\\" + rapor + ".jrxml";
		File file = ResourceUtils.getFile(path);
		final byte[] rawBytes;

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String, Object> parameters = parametre;
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRDocxExporter exporter = new JRDocxExporter(); // initialize exporter

		try (ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
			jasperPrint.setProperty(net.sf.jasperreports.engine.JRParameter.IS_IGNORE_PAGINATION, "true");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
			exporter.exportReport();

			rawBytes = xlsReport.toByteArray();
		}

		return rawBytes;
	}

	public byte[] exportReportXslx(String rapor, Collection<?> list, Map<String, Object> parametre) throws Exception {
		String path = "src\\main\\java\\enerji\\util\\rapor\\" + rapor + ".jrxml";
		File file = ResourceUtils.getFile(path);
		final byte[] rawBytes;

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String, Object> parameters = parametre;
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter

		try (ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
			jasperPrint.setProperty(net.sf.jasperreports.engine.JRParameter.IS_IGNORE_PAGINATION, "true");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
			SimpleXlsxReportConfiguration xlsxreportConfig = new SimpleXlsxReportConfiguration();// setup configuration
			xlsxreportConfig.setSheetNames(new String[] { rapor + " Raporu" });
			xlsxreportConfig.setRemoveEmptySpaceBetweenRows(true);
			xlsxreportConfig.setForcePageBreaks(false);
			xlsxreportConfig.setWrapText(false);
			xlsxreportConfig.setCollapseRowSpan(true);
			xlsxreportConfig.setOnePagePerSheet(true);
			xlsxreportConfig.setDetectCellType(true);
			exporter.setConfiguration(xlsxreportConfig);
			exporter.exportReport();

			rawBytes = xlsReport.toByteArray();
		}

		return rawBytes;
	}

	// Decp
	public String exportReportXsl(String rapor, Collection<?> list) throws Exception {
		String path = "src\\main\\java\\enerji\\util\\rapor\\" + rapor + ".jrxml";
		File file = ResourceUtils.getFile(path);

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRXlsExporter exporter = new JRXlsExporter(); // initialize exporter
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); // set compiled report as input
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "\\Personel.xls")); // path with
																									// filename
		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(true); // setup configuration
		configuration.setDetectCellType(true);
		exporter.setConfiguration(configuration); // set configuration
		exporter.exportReport();

		return "report generated in path : " + path;
	}

}