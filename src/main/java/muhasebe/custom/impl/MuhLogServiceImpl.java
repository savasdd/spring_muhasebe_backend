package muhasebe.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.Service;
import muhasebe.custom.async.MuhLogServiceAsync;
import muhasebe.dto.MuhIslemLogDto;
import muhasebe.model.MuhHataLog;
import muhasebe.model.MuhIslemLog;
import muhasebe.model.MuhKullaniciLog;
import muhasebe.repository.MuhHataLogRepository;
import muhasebe.repository.MuhIslemLogRepository;
import muhasebe.repository.MuhKullaniciLogRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;

@Slf4j
@Component
public class MuhLogServiceImpl implements MuhLogServiceAsync {

	private static final String KULLANICI_INDEX = "kullanicilog";
	private static final String ISLEM_INDEX = "islemlog";

	private ElasticsearchOperations operasyon;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public Service service;

	@Autowired
	private MuhKullaniciLogRepository user;

	@Autowired
	private MuhHataLogRepository hata;

	@Autowired
	private MuhIslemLogRepository islem;

	@Autowired
	private CustomMapper mapper;

	@Override
	public MuhKullaniciLog createKullanici(MuhKullaniciLog model) throws MUHException {
		MuhKullaniciLog kullanici = user.save(model);
		log.info("Create Kullanici Log!");
		return kullanici;
	}

	@Override
	public List<MuhKullaniciLog> getKullanici() throws MUHException {
		Page<MuhKullaniciLog> list = user.findAll(PageRequest.of(0, 500));
		log.info("Log User Size: " + list.getContent().size());
		return list.getContent();
	}

	@Override
	public MuhKullaniciLog updateKullanici(String id, MuhKullaniciLog model) throws MUHException {
		Optional<MuhKullaniciLog> logs = user.findById(id);
		MuhKullaniciLog logToUpdate = logs.map(val -> {
			val.setKullaniciAdi(model.getKullaniciAdi());
			val.setCreateDate(model.getCreateDate());
			val.setExpireDate(model.getExpireDate());
			return val;
		}).orElseThrow(IllegalArgumentException::new);

		log.info("Update Kullanici Log");
		return user.save(logToUpdate);
	}

	@Override
	public MuhKullaniciLog deleteKullanici(String id) throws MUHException {
		Optional<MuhKullaniciLog> exit = user.findById(id);
		MuhKullaniciLog dto = exit.get();
		user.delete(dto);
		return dto;
	}

	@Override
	public List<SearchHit<MuhKullaniciLog>> kullaniciSearch(String search) throws MUHException {
		return null;
	}

	@Override
	public MuhHataLog createHata(MuhHataLog model) throws MUHException {
		MuhHataLog newLog = hata.save(model);
		log.info("Create Hata Log!");
		return newLog;
	}

	@Override
	public List<MuhHataLog> getHata() throws MUHException {
		Iterable<MuhHataLog> list = hata.findAll();
		List<MuhHataLog> result = new ArrayList<MuhHataLog>();
		list.forEach(result::add);

		log.info("Log Hata Size: " + result.size());
		return result;
	}

	@Override
	public MuhIslemLog createIslem(MuhIslemLog model) throws MUHException {
		MuhIslemLog newLog = islem.save(model);
		log.info("Create Islem Log!");
		return newLog;
	}

	@Override
	public List<MuhIslemLogDto> getIslem() throws MUHException {
		Iterable<MuhIslemLog> list = islem.findAll();
		List<MuhIslemLogDto> result = mapper.mapAll(list, MuhIslemLogDto.class);

		log.info("Log Islem Size: " + result.size());
		return result;
	}

	@Override
	public MuhKullaniciLog getNativeQuery() throws MUHException {
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("kullaniciAdi", "muh");
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

		SearchHits<MuhKullaniciLog> list = operasyon.search(searchQuery, MuhKullaniciLog.class,
				IndexCoordinates.of(KULLANICI_INDEX));

		for (SearchHit<MuhKullaniciLog> s : list) {
			System.err.println(s.getContent());
		}

		return null;
	}

	@Override
	public MuhKullaniciLog getStringQuery() throws MUHException {
		StringQuery searchQuery = new StringQuery("{\"match\":{\"kullaniciAdi\":{\"query\":\"" + "muh" + "\"}}}\"");

		SearchHits<MuhKullaniciLog> list = operasyon.search(searchQuery, MuhKullaniciLog.class,
				IndexCoordinates.of(KULLANICI_INDEX));
		for (SearchHit<MuhKullaniciLog> s : list) {
			System.err.println(s.getContent());
		}
		return null;
	}

	@Override
	public MuhKullaniciLog getCriteriaQuery() throws MUHException {
		Criteria criteria = new Criteria("metot").and("GET");
		CriteriaQuery searchQuery = new CriteriaQuery(criteria);

		SearchHits<MuhIslemLog> list = operasyon.search(searchQuery, MuhIslemLog.class,
				IndexCoordinates.of(ISLEM_INDEX));
		return null;
	}

	public MuhKullaniciLog searchDateRange(String fromDate, String toDate, Integer offset, Integer limit) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("user");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		sourceBuilder.query(QueryBuilders.rangeQuery("dateOfBirth").gte(fromDate).lte(toDate));
		searchRequest.source(sourceBuilder);
		log.info("Search JSON query: {}", searchRequest.source().toString());

		return null;
	}
}
