package muhasebe.custom.async;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhKiraciDto;
import muhasebe.model.MuhKiraci;
import muhasebe.util.exception.MUHException;

public interface MuhKiraciServiceAsync {

	public Generic<MuhKiraciDto> getKiracis(String search, Pageable page) throws MUHException;

	public MuhKiraciDto getKiraciById(String id) throws MUHException;

	public MuhKiraciDto createKiraci(MuhKiraci model) throws MUHException;

	public MuhKiraciDto updateKiraci(String id, MuhKiraci model) throws MUHException;

	public MuhKiraciDto deleteKiraci(String id) throws MUHException;

	public MuhKiraci getKiraci(String id) throws MUHException;

}
