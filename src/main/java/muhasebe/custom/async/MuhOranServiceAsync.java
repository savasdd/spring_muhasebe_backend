package muhasebe.custom.async;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhOranDto;
import muhasebe.model.MuhOran;
import muhasebe.util.exception.MUHException;

public interface MuhOranServiceAsync {

	public Generic<MuhOranDto> getOrans(String search, Pageable page) throws MUHException;

	public MuhOranDto getOranById(String id) throws MUHException;

	public MuhOranDto createOran(MuhOran model) throws MUHException;

	public MuhOranDto updateOran(String id, MuhOran model) throws MUHException;

	public MuhOranDto deleteOran(String id) throws MUHException;

	public MuhOran getOran(String id) throws MUHException;

}
