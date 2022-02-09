package muhasebe.custom.async;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhHesapDto;
import muhasebe.model.MuhHesap;
import muhasebe.util.exception.MUHException;

public interface MuhHesapServiceAsync {

	public Generic<MuhHesapDto> getHesaps(String search, Pageable page) throws MUHException;

	public MuhHesapDto getHesapById(String id) throws MUHException;

	public MuhHesapDto createHesap(MuhHesap model) throws MUHException;

	public MuhHesapDto updateHesap(String id, MuhHesap model) throws MUHException;

	public MuhHesapDto deleteHesap(String id) throws MUHException;

}
