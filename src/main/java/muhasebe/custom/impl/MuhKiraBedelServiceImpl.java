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
import muhasebe.custom.async.MuhKiraBedelServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhKiraBedelDto;
import muhasebe.model.MuhKiraBedel;
import muhasebe.repository.MuhKiraBedelRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhKiraBedelServiceImpl implements MuhKiraBedelServiceAsync {

	@Autowired
	private CustomMapper mapper;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Service service;

	@Autowired
	private MuhKiraBedelRepository repository;

	@Override
	public Generic<MuhKiraBedelDto> getBedels(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhKiraBedelDto getBedelById(String kiraciId, String id) throws MUHException {
		return mapper.map(repository.findByKiraId(id), MuhKiraBedelDto.class);
	}

	@Override
	public MuhKiraBedelDto createBedel(String kiraciId, MuhKiraBedel model) throws MUHException {
		model.setKiraci(kiraciId != null ? service.getKiraci().getKiraci(kiraciId) : null);
		model.setFaizOrani(
				model.getFaizOrani().getOranId() != null ? service.getOran().getOran(model.getFaizOrani().getOranId())
						: null);

		MuhKiraBedel dto = MuhKiraBedel.getInstance();
		dto.setBedel(model.getBedel());
		dto.setToplamBedel(model.getToplamBedel());
		dto.setFaizOrani(model.getFaizOrani());
		dto.setKiraci(model.getKiraci());
		dto.setKiralamaTarihi(model.getKiralamaTarihi());
		dto.setKiraSuresi(model.getKiraSuresi());
		dto.setTanim(model.getTanim());
		dto.setVersion(0L);

		MuhKiraBedel bedel = repository.save(dto);
		log.info("BEDEL: =====> " + bedel.getKiraId() + " eklendi!");
		return mapper.map(bedel, MuhKiraBedelDto.class);
	}

	@Override
	public MuhKiraBedelDto updateBedel(String kiraciId, String id, MuhKiraBedel model) throws MUHException {
		MuhKiraBedel exist = repository.findByKiraciAndBedel(kiraciId, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		lockEntity(exist);

		model.setKiraci(kiraciId != null ? service.getKiraci().getKiraci(kiraciId) : null);
		model.setFaizOrani(
				model.getFaizOrani().getOranId() != null ? service.getOran().getOran(model.getFaizOrani().getOranId())
						: null);
		exist.setTanim(model.getTanim());
		exist.setBedel(model.getBedel());
		exist.setFaizOrani(model.getFaizOrani());
		exist.setKiraci(model.getKiraci());
		exist.setKiralamaTarihi(model.getKiralamaTarihi());
		exist.setKiraSuresi(model.getKiraSuresi());
		exist.setToplamBedel(model.getToplamBedel());
		MuhKiraBedel bedel = repository.save(exist);
		log.info("BEDEL: =====> " + bedel.getKiraId() + " güncellendi!");
		return mapper.map(bedel, MuhKiraBedelDto.class);
	}

	@Override
	public MuhKiraBedelDto deleteBedel(String kiraciId, String id) throws MUHException {
		MuhKiraBedel bedel = repository.findByKiraciAndBedel(kiraciId, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		repository.delete(bedel);
		log.info("BEDEL: =====> " + id + " silindi!");
		return mapper.map(bedel, MuhKiraBedelDto.class);
	}

	@Transactional
	private void lockEntity(MuhKiraBedel model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhKiraBedel! ");
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhKiraBedelDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhKiraBedel>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhKiraBedel>(
					page);
			CriteriaQuery<MuhKiraBedel> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhKiraBedel> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhKiraBedelDto> generic = new Generic<MuhKiraBedelDto>();
			generic.setData(mapper.mapAll(list, MuhKiraBedelDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhHesap Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhKiraBedelDto> jpa(Pageable page) throws MUHException {
		Page<MuhKiraBedel> list = repository.findAll(page);
		Generic<MuhKiraBedelDto> generic = new Generic<MuhKiraBedelDto>();
		generic.setData(mapper.mapAll(list, MuhKiraBedelDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		return generic;
	}

}
