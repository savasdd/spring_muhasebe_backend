package muhasebe.custom.async;

import org.springframework.data.domain.Pageable;

import muhasebe.dto.Generic;
import muhasebe.dto.MuhKullaniciDto;
import muhasebe.model.MuhKullanici;
import muhasebe.util.exception.MUHException;

public interface MuhKullaniciServiceAsync {

	public Generic<MuhKullaniciDto> getKullanicis(String search, Pageable page) throws MUHException;

	public MuhKullaniciDto getKullaniciById(Long id) throws MUHException;

	public MuhKullaniciDto createKullanici(MuhKullanici model) throws MUHException;

	public MuhKullaniciDto updateKullanici(Long id, MuhKullanici model) throws MUHException;

	public MuhKullaniciDto deleteKullanici(Long id) throws MUHException;

	public MuhKullaniciDto findKullaniciAdi(String userName);

}
