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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.Service;
import muhasebe.custom.async.MuhKiraciServiceAsync;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhKiraBedelDto;
import muhasebe.dto.MuhKiraciDto;
import muhasebe.model.MuhKiraci;
import muhasebe.repository.MuhKiraciRepository;
import muhasebe.util.CustomMapper;
import muhasebe.util.exception.MUHException;
import muhasebe.util.rsql.jpa.JpaCriteriaQueryVisitor;

@Slf4j
@Component
public class MuhKiraciServiceImpl implements MuhKiraciServiceAsync {

	@Autowired
	private CustomMapper mapper;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public Service service;

	@Autowired
	private MuhKiraciRepository repository;

	@Override
	public Generic<MuhKiraciDto> getKiracis(String search, Pageable page) throws MUHException {
		return search != null ? rsql(search, page) : jpa(page);
	}

	@Override
	public MuhKiraciDto getKiraciById(String id) throws MUHException {
		return mapper.map(repository.findByKiraciId(id), MuhKiraciDto.class);
	}

	@Override
	public MuhKiraciDto createKiraci(MuhKiraci model) throws MUHException {
		model.setCinsiyet(
				model.getCinsiyet().getKodId() != null ? service.getKod().getKod(model.getCinsiyet().getKodId())
						: null);
		MuhKiraci dto = MuhKiraci.getInstance();
		dto.setAd(model.getAd());
		dto.setSoyad(model.getSoyad());
		dto.setSicilNo(model.getSicilNo());
		dto.setCinsiyet(model.getCinsiyet());
		dto.setDogumTarihi(model.getDogumTarihi());
		dto.setDogumYeri(model.getDogumYeri());
		dto.setKurum(model.getKurum());
		dto.setTckn(model.getTckn());
		dto.setVersion(0L);

		MuhKiraci kiraci = repository.save(dto);
		log.info("KIRACI: =====> " + kiraci.getKiraciId() + " eklendi!");
		return mapper.map(kiraci, MuhKiraciDto.class);
	}

	@Override
	public MuhKiraciDto updateKiraci(String id, MuhKiraci model) throws MUHException {
		MuhKiraci exist = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		lockEntity(exist);
		model.setCinsiyet(
				model.getCinsiyet().getKodId() != null ? service.getKod().getKod(model.getCinsiyet().getKodId())
						: null);

		exist.setAd(model.getAd());
		exist.setSoyad(model.getSoyad());
		exist.setTckn(model.getTckn());
		exist.setSicilNo(model.getSicilNo());
		exist.setDogumTarihi(model.getDogumTarihi());
		exist.setDogumYeri(model.getDogumYeri());
		exist.setCinsiyet(model.getCinsiyet());
		MuhKiraci kiraci = repository.save(exist);
		log.info("KIRACI: =====> " + kiraci.getKiraciId() + " güncellendi!");
		return mapper.map(kiraci, MuhKiraciDto.class);
	}

	@Override
	public MuhKiraciDto deleteKiraci(String id) throws MUHException {
		Generic<MuhKiraBedelDto> list = service.getBedel().getBedels("kiraci.kiraciId==" + id, PageRequest.of(0, 100));
		if (list.getData().size() > 0)
			throw new MUHException("Kiracıya Bağlı Bedel Kayıtlar Var!");

		MuhKiraci kiraci = repository.findByKiraciId(id);
		repository.delete(kiraci);
		log.info("KIRACI: =====> " + id + " silindi!");
		return mapper.map(kiraci, MuhKiraciDto.class);
	}

	@Transactional
	private void lockEntity(MuhKiraci model) throws MUHException {
		manager.lock(model, LockModeType.OPTIMISTIC);
		log.info("Lock MuhKiraci! ");
	}

	@Override
	public MuhKiraci getKiraci(String id) {
		return repository.findByKiraciId(id);
	}

	@SuppressWarnings("unchecked")
	public Generic<MuhKiraciDto> rsql(String search, Pageable page) throws MUHException {
		try {
			Node node = new RSQLParser().parse(search);
			RSQLVisitor<CriteriaQuery<MuhKiraci>, EntityManager> visitor = new JpaCriteriaQueryVisitor<MuhKiraci>(page);
			CriteriaQuery<MuhKiraci> criteria = node.accept(visitor, manager);

			Query query = manager.createQuery(criteria);
			Long totalCount = (long) query.getResultList().size();
			query.setFirstResult((page.getPageNumber() > 0 ? (page.getPageNumber() - 1) : 0) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			List<MuhKiraci> list = query.getResultList();

			int fullPage = (int) (totalCount / page.getPageSize());
			int partialPage = ((totalCount % page.getPageSize()) > 0) ? (1) : (0);
			int totalPage = fullPage + partialPage;

			Generic<MuhKiraciDto> generic = new Generic<MuhKiraciDto>();
			generic.setData(mapper.mapAll(list, MuhKiraciDto.class));
			generic.setTotalCount(totalCount);
			generic.setTotalPage(totalPage);
			log.info("MuhKiraci Size: " + totalCount);
			return generic;
		} catch (IllegalArgumentException e) {
			throw new MUHException(e.getMessage());
		}
	}

	public Generic<MuhKiraciDto> jpa(Pageable page) throws MUHException {
		Page<MuhKiraci> list = repository.findAll(page);
		Generic<MuhKiraciDto> generic = new Generic<MuhKiraciDto>();
		generic.setData(mapper.mapAll(list, MuhKiraciDto.class));
		generic.setTotalCount(list.getTotalElements());
		generic.setTotalPage(list.getTotalPages());
		return generic;
	}

}
