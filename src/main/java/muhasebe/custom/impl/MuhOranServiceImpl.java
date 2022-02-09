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
import muhasebe.custom.async.MuhOranServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhOranDto;
import muhasebe.model.MuhOran;
import muhasebe.repository.MuhOranRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhOranServiceImpl implements MuhOranServiceAsync {

	@Autowired
	private CustomMapper mapper;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MuhOranRepository repository;

	@Override
	public Generic<MuhOranDto> getOrans(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhOranDto getOranById(String id) throws MUHException {
		return mapper.map(repository.findByOranId(id), MuhOranDto.class);
	}

	@Override
	public MuhOranDto createOran(MuhOran model) throws MUHException {
		MuhOran dto = MuhOran.getInstance();
		dto.setAktif(model.getAktif());
		dto.setOran(model.getOran());
		dto.setTanim(model.getTanim());
		dto.setYil(model.getYil());
		dto.setVersion(0L);

		MuhOran oran = repository.save(dto);
		log.info("ORAN: =====> " + oran.getOranId() + " eklendi!");
		return mapper.map(oran, MuhOranDto.class);
	}

	@Override
	public MuhOranDto updateOran(String id, MuhOran model) throws MUHException {
		MuhOran exist = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		lockEntity(exist);

		exist.setAktif(model.getAktif());
		exist.setOran(model.getOran());
		exist.setTanim(model.getTanim());
		exist.setYil(model.getYil());
		MuhOran oran = repository.save(exist);
		log.info("ORAN: =====> " + oran.getOranId() + " güncellendi!");
		return mapper.map(oran, MuhOranDto.class);
	}

	@Override
	public MuhOranDto deleteOran(String id) throws MUHException {
		MuhOran oran = repository.findByOranId(id);
		repository.delete(oran);
		log.info("ORAN: =====> " + id + " silindi!");
		return mapper.map(oran, MuhOranDto.class);
	}

	@Transactional
	private void lockEntity(MuhOran model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhOran! ");
	}

	@Override
	public MuhOran getOran(String id) throws MUHException {
		return repository.findByOranId(id);
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhOranDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhOran>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhOran>(page);
			CriteriaQuery<MuhOran> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhOran> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhOranDto> generic = new Generic<MuhOranDto>();
			generic.setData(mapper.mapAll(list, MuhOranDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhOran Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhOranDto> jpa(Pageable page) throws MUHException {
		Page<MuhOran> list = repository.getPageble(page);
		Generic<MuhOranDto> generic = new Generic<MuhOranDto>();
		generic.setData(mapper.mapAll(list, MuhOranDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		return generic;
	}

}
