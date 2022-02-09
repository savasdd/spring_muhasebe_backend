package muhasebe.custom.async;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHit;

import muhasebe.dto.MuhIslemLogDto;
import muhasebe.model.MuhHataLog;
import muhasebe.model.MuhIslemLog;
import muhasebe.model.MuhKullaniciLog;
import muhasebe.util.exception.MUHException;

public interface MuhLogServiceAsync {

	public MuhKullaniciLog createKullanici(MuhKullaniciLog model) throws MUHException;

	public List<MuhKullaniciLog> getKullanici() throws MUHException;

	public MuhKullaniciLog updateKullanici(String id, MuhKullaniciLog model) throws MUHException;

	public MuhKullaniciLog deleteKullanici(String id) throws MUHException;

	public List<SearchHit<MuhKullaniciLog>> kullaniciSearch(String search) throws MUHException;

	public MuhHataLog createHata(MuhHataLog model) throws MUHException;

	public List<MuhHataLog> getHata() throws MUHException;

	public MuhIslemLog createIslem(MuhIslemLog model) throws MUHException;

	public List<MuhIslemLogDto> getIslem() throws MUHException;

	public MuhKullaniciLog getNativeQuery() throws MUHException;

	public MuhKullaniciLog getStringQuery() throws MUHException;

	public MuhKullaniciLog getCriteriaQuery() throws MUHException;
}
