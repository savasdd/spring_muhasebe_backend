package muhasebe.custom.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.async.MuhRolServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhRolDto;
import muhasebe.model.MuhRol;
import muhasebe.repository.MuhRolRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhRolServiceImpl implements MuhRolServiceAsync {

	@Autowired
	private CustomMapper mapper;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MuhRolRepository repository;

	@Override
	public Generic<MuhRolDto> getRols(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhRolDto getRolById(String id) throws MUHException {
		return mapper.map(repository.findById(id), MuhRolDto.class);
	}

	@Override
	public MuhRolDto createRol(MuhRol model) throws MUHException {
		MuhRol dto = MuhRol.getInstance();
		dto.setAciklama(model.getAciklama());
		dto.setAktif(model.getAktif());
		dto.setTanim(model.getTanim());
		dto.setKod(model.getKod());
		dto.setVersion(0L);

		MuhRol rol = repository.save(dto);
		log.info("ROL: =====> " + rol.getRolId() + " eklendi!");
		return mapper.map(rol, MuhRolDto.class);
	}

	@Override
	public MuhRolDto updateRol(String id, MuhRol model) throws MUHException {
		Optional<MuhRol> rols = repository.findById(id);
		lockEntity(rols.get());

		MuhRol rolToUpdate = rols.map(val -> {
			val.setAciklama(model.getAciklama());
			val.setAktif(model.getAktif());
			val.setTanim(model.getTanim());
			val.setKod(model.getKod());
			return val;
		}).orElseThrow(IllegalArgumentException::new);

		MuhRol rol = repository.save(rolToUpdate);
		log.info("ROL: =====> " + rol.getRolId() + " güncellendi!");
		return mapper.map(rol, MuhRolDto.class);
	}

	@Override
	public MuhRolDto deleteRol(String id) throws MUHException {
		MuhRol rol = repository.findByRolId(id);
		repository.delete(rol);
		log.info("ROL: =====> " + id + " silindi!");
		return mapper.map(rol, MuhRolDto.class);
	}

	@Transactional
	private void lockEntity(MuhRol model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhRol! ");
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhRolDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhRol>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhRol>(page);
			CriteriaQuery<MuhRol> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhRol> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhRolDto> generic = new Generic<MuhRolDto>();
			generic.setData(mapper.mapAll(list, MuhRolDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhRol Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhRolDto> jpa(Pageable page) throws MUHException {
		Page<MuhRol> list = repository.findAll(page);
		Generic<MuhRolDto> generic = new Generic<MuhRolDto>();
		generic.setData(mapper.mapAll(list, MuhRolDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		return generic;
	}

}
