package muhasebe.custom.async;

import java.util.List;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhKodDto;
import muhasebe.model.MuhKod;
import muhasebe.util.exception.MUHException;

public interface MuhKodServiceAsync {

	public Generic<MuhKodDto> getKods(String search, Pageable page) throws MUHException;

	public MuhKodDto getKodById(String id) throws MUHException;

	public MuhKodDto createKod(MuhKod model) throws MUHException;

	public MuhKodDto updateKod(String id, MuhKod model) throws MUHException;

	public MuhKodDto deleteKod(String id) throws MUHException;

	public MuhKod getKod(String id) throws MUHException;

	public List<MuhKodDto> getKodByUstId(String id) throws MUHException;

}
