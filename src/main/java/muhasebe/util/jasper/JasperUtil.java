package muhasebe.util.jasper;

import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import muhasebe.util.exception.MUHException;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class JasperUtil {

	private JasperPrint jasperPrint;

	public JasperUtil() {
	}

	public JasperUtil(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public <E> byte[] export(String kod, String raporAdi, Map<String, Object> parameters) throws MUHException {
		byte[] bytes = null;

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(JasperConfig.class);
		ctx.refresh();

		ReportFilter filter = ctx.getBean(ReportFilter.class);
		filter.setReportFileName(raporAdi + ".jasper");
		filter.loadReport();

		// filter.setReportFileName(raporAdi + ".jrxml");
		// filter.compileReport();

		filter.setParameters(parameters);
		filter.fillReport();

		ReportExporter exporter = ctx.getBean(ReportExporter.class);
		exporter.setJasperPrint(filter.getJasperPrint());

		if (kod != null && kod.equals("PDF"))
			bytes = exporter.exportToPdf(raporAdi + ".pdf", raporAdi);

		if (kod != null && kod.equals("WORD"))
			bytes = exporter.exportToDocx(raporAdi + ".docx", raporAdi);

		if (kod != null && kod.equals("EXCEL"))
			bytes = exporter.exportToXlsx(raporAdi + ".xlsx", raporAdi);

		return bytes;
	}
}
