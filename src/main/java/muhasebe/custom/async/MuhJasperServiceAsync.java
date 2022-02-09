package muhasebe.custom.async;

import muhasebe.dto.MuhJasperDto;
import muhasebe.util.exception.MUHException;

public interface MuhJasperServiceAsync {

	public byte[] jasper(String turu, String ekran, String adi, MuhJasperDto params) throws MUHException;

}
