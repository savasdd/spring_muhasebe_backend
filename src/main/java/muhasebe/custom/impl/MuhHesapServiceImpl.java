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
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.Service;
import muhasebe.custom.async.MuhHesapServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhHesapDto;
import muhasebe.model.MuhHesap;
import muhasebe.repository.MuhHesapRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhHesapServiceImpl implements MuhHesapServiceAsync {

	/*
	 * Kullanılmayan sınıfların Autowired edilmesi servis hatasına neden olur. Aynı
	 * kayıt üzerinde iki kişinin aynı aynda işlem yapmasını engellemek adına jpa
	 * version ve manager lock metotu kullanılıyor.
	 */

	@Autowired
	private CustomMapper mapper;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public Service service;

	@Autowired
	private MuhHesapRepository repository;

	@Override
	public Generic<MuhHesapDto> getHesaps(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhHesapDto getHesapById(String id) throws MUHException {
		return mapper.map(repository.findByHesapId(id), MuhHesapDto.class);
	}

	@Override
	public MuhHesapDto createHesap(MuhHesap model) throws MUHException {
		model.setKod(model.getKod() != null
				? model.getKod().getKodId() != null ? service.getKod().getKod(model.getKod().getKodId()) : null
				: null);

		MuhHesap dto = MuhHesap.getInstance();
		dto.setAktif(model.getAktif());
		dto.setHesapNo(model.getHesapNo());
		dto.setKod(model.getKod());
		dto.setTanim(model.getTanim());
		dto.setVersion(0L);

		MuhHesap hesap = repository.save(dto);
		log.info("HESAP: =====> " + hesap.getHesapId() + " eklendi!");
		return mapper.map(hesap, MuhHesapDto.class);
	}

	@Override
	public MuhHesapDto updateHesap(String id, MuhHesap model) throws MUHException {
		MuhHesap exist = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		lockEntity(exist);
		
		System.err.println(model.toString());

		model.setKod(model.getKod().getKodId() != null ? service.getKod().getKod(model.getKod().getKodId()) : null);
		exist.setKod(model.getKod());
		exist.setAktif(model.getAktif());
		exist.setHesapNo(model.getHesapNo());
		exist.setTanim(model.getTanim());
		MuhHesap hesap = repository.save(exist);
		log.info("HESAP: =====> " + hesap.getHesapId() + " güncellendi!");
		return mapper.map(hesap, MuhHesapDto.class);
	}

	@Override
	public MuhHesapDto deleteHesap(String id) throws MUHException {
		MuhHesap hesap = repository.findByHesapId(id);
		repository.delete(hesap);
		log.info("HESAP: =====> " + id + " silindi!");
		return mapper.map(hesap, MuhHesapDto.class);
	}

	@Transactional
	private void lockEntity(MuhHesap model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhHesap! ");
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhHesapDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhHesap>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhHesap>(page);
			CriteriaQuery<MuhHesap> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhHesap> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhHesapDto> generic = new Generic<MuhHesapDto>();
			generic.setData(mapper.mapAll(list, MuhHesapDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhHesap Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhHesapDto> jpa(Pageable page) throws MUHException {
		Page<MuhHesap> list = repository.findAll(page);
		Generic<MuhHesapDto> generic = new Generic<MuhHesapDto>();
		generic.setData(mapper.mapAll(list, MuhHesapDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		return generic;
	}

}
