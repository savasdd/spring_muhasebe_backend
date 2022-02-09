package muhasebe.util.jasper;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import muhasebe.util.exception.MUHException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@Component
public class ReportFilter {

	private String reportFileName;

	private JasperReport jasperReport;

	private JasperPrint jasperPrint;

	@Autowired
	private DataSource dataSource;

	private Map<String, Object> parameters;

	public ReportFilter() {
		parameters = new HashMap<>();
	}

	public void prepareReport() throws Exception {
		compileReport();
		fillReport();
	}

	public void compileReport() throws Exception {
		try {
			InputStream reportStream = getClass().getResourceAsStream("/".concat(reportFileName));
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRSaver.saveObject(jasperReport, reportFileName.replace(".jrxml", ".jasper"));

		} catch (JRException ex) {
			ex.printStackTrace();
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void loadReport() throws MUHException {
		try {
			InputStream jasperStream = getClass().getResourceAsStream("/".concat(reportFileName));
			jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		} catch (JRException ex) {
			ex.printStackTrace();
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void fillReport() {
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
		} catch (JRException | SQLException ex) {
			Logger.getLogger(ReportFilter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}