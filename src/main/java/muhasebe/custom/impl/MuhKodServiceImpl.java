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
import muhasebe.custom.async.MuhKodServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhKodDto;
import muhasebe.model.MuhKod;
import muhasebe.repository.MuhKodRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhKodServiceImpl implements MuhKodServiceAsync {

	@Autowired
	private CustomMapper mapper;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MuhKodRepository repository;

	@Override
	public Generic<MuhKodDto> getKods(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhKodDto getKodById(String id) throws MUHException {
		return mapper.map(repository.findByKodId(id), MuhKodDto.class);
	}

	@Override
	public MuhKodDto createKod(MuhKod model) throws MUHException {

		if (model.getTanim() == null || model.getTanim().isEmpty())
			throw new MUHException("Tanım Boş Geçilemez");

		MuhKod dto = MuhKod.getInstance();
		dto.setUstKod(model.getUstKod());
		dto.setAciklama(model.getAciklama());
		dto.setKod(model.getKod());
		dto.setSiraNo(model.getSiraNo());
		dto.setTanim(model.getTanim());
		dto.setVersion(0L);

		MuhKod kod = repository.save(dto);
		log.info("KOD: =====> " + kod.getKodId() + " eklendi!");
		return mapper.map(kod, MuhKodDto.class);
	}

	@Override
	public MuhKodDto updateKod(String id, MuhKod model) throws MUHException {
		MuhKod exist = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		lockEntity(exist);

		exist.setKodId(exist.getKodId());
		exist.setUstKod(model.getUstKod());
		exist.setAciklama(model.getAciklama());
		exist.setKod(model.getKod());
		exist.setSiraNo(model.getSiraNo());
		exist.setTanim(model.getTanim());
		MuhKod kod = repository.save(exist);
		log.info("KOD: =====> " + kod.getKodId() + " güncellendi!");
		return mapper.map(kod, MuhKodDto.class);
	}

	@Override
	public MuhKodDto deleteKod(String id) throws MUHException {
		MuhKod kod = repository.findByKodId(id);
		repository.delete(kod);
		log.info("KOD: =====> " + id + " silindi!");
		return mapper.map(kod, MuhKodDto.class);
	}

	@Transactional
	private void lockEntity(MuhKod model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhKod! ");
	}

	@Override
	public MuhKod getKod(String id) {
		return repository.findByKodId(id);
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhKodDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhKod>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhKod>(page);
			CriteriaQuery<MuhKod> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhKod> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhKodDto> generic = new Generic<MuhKodDto>();
			generic.setData(mapper.mapAll(list, MuhKodDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhHesap Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhKodDto> jpa(Pageable page) throws MUHException {
		Page<MuhKod> list = repository.findAll(page);
		Generic<MuhKodDto> generic = new Generic<MuhKodDto>();
		generic.setData(mapper.mapAll(list, MuhKodDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		return generic;
	}

	@Override
	public List<MuhKodDto> getKodByUstId(String id) throws MUHException {
		log.info("Get MuhKodDto UstId: " + id);
		return mapper.mapAll(repository.findKodByUstKod(id), MuhKodDto.class);
	}

}
