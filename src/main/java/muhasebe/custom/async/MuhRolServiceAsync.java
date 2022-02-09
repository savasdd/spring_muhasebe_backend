package muhasebe.custom.async;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhRolDto;
import muhasebe.model.MuhRol;
import muhasebe.util.exception.MUHException;

public interface MuhRolServiceAsync {

	public Generic<MuhRolDto> getRols(String search, Pageable page) throws MUHException;

	public MuhRolDto getRolById(String id) throws MUHException;

	public MuhRolDto createRol(MuhRol model) throws MUHException;

	public MuhRolDto updateRol(String id, MuhRol model) throws MUHException;

	public MuhRolDto deleteRol(String id) throws MUHException;

}
