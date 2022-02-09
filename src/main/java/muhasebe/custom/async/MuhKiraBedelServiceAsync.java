package muhasebe.custom.async;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhKiraBedelDto;
import muhasebe.model.MuhKiraBedel;
import muhasebe.util.exception.MUHException;

public interface MuhKiraBedelServiceAsync {

	public Generic<MuhKiraBedelDto> getBedels(String search, Pageable page) throws MUHException;

	public MuhKiraBedelDto getBedelById(String kiraciId, String id) throws MUHException;

	public MuhKiraBedelDto createBedel(String kiraciId, MuhKiraBedel model) throws MUHException;

	public MuhKiraBedelDto updateBedel(String kiraciId, String id, MuhKiraBedel model) throws MUHException;

	public MuhKiraBedelDto deleteBedel(String kiraciId, String id) throws MUHException;

}
