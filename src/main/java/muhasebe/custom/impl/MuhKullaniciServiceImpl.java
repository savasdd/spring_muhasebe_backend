package muhasebe.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.async.MuhKullaniciServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhKullaniciDto;
import muhasebe.model.MuhKullanici;
import muhasebe.repository.MuhKullaniciRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhKullaniciServiceImpl implements MuhKullaniciServiceAsync {

	@Autowired
	private CustomMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MuhKullaniciRepository repository;

	@Override
	public Generic<MuhKullaniciDto> getKullanicis(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhKullaniciDto getKullaniciById(String id) throws MUHException {
		return mapper.map(repository.findByKullaniciId(id), MuhKullaniciDto.class);
	}

	@Override
	public MuhKullaniciDto createKullanici(MuhKullanici model) throws MUHException {
		model.setSifre(passwordEncoder.encode(model.getSifre()));

		MuhKullanici dto = MuhKullanici.getInstance();
		dto.setAd(model.getAd());
		dto.setSoyad(model.getSoyad());
		dto.setKullaniciAdi(model.getKullaniciAdi());
		dto.setSifre(model.getSifre());
		dto.setTckn(model.getTckn());
		dto.setVersion(0l);

		MuhKullanici kullanici = repository.save(dto);
		log.info("KULLANICI: =====> " + kullanici.getKullaniciId() + " eklendi!");
		return mapper.map(kullanici, MuhKullaniciDto.class);
	}

	@Override
	public MuhKullaniciDto updateKullanici(String id, MuhKullanici model) throws MUHException {
		MuhKullanici exist = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		lockEntity(exist);

		exist.setAd(model.getAd());
		exist.setSoyad(model.getSoyad());
		exist.setTckn(model.getTckn());
		exist.setKullaniciAdi(model.getKullaniciAdi());
		MuhKullanici kullanici = repository.save(exist);
		log.info("KULLANICI: =====> " + kullanici.getKullaniciId() + " güncellendi!");
		return mapper.map(kullanici, MuhKullaniciDto.class);
	}

	@Override
	public MuhKullaniciDto deleteKullanici(String id) throws MUHException {
		MuhKullanici kullanici = repository.findByKullaniciId(id);
		repository.delete(kullanici);
		log.info("KULLANICI: =====> " + id + " silindi!");
		return mapper.map(kullanici, MuhKullaniciDto.class);
	}

	@Transactional
	private void lockEntity(MuhKullanici model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhKullanici!");
	}

	@Override
	public MuhKullaniciDto findKullaniciAdi(String userName) {
		MuhKullanici user = repository.getByKullaniciAdi(userName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı Bulunmadı!"));
		return mapper.map(user, MuhKullaniciDto.class);
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhKullaniciDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhKullanici>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhKullanici>(
					page);
			CriteriaQuery<MuhKullanici> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhKullanici> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhKullaniciDto> generic = new Generic<MuhKullaniciDto>();
			generic.setData(mapper.mapAll(list, MuhKullaniciDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhHesap Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhKullaniciDto> jpa(Pageable page) throws MUHException {
		Page<MuhKullanici> list = repository.getPageble(page);
		Generic<MuhKullaniciDto> generic = new Generic<MuhKullaniciDto>();
		generic.setData(mapper.mapAll(list, MuhKullaniciDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		log.info("Get Kullanici!");
		return generic;
	}

}
