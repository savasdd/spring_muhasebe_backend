package muhasebe.custom.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.Service;
import muhasebe.custom.async.MuhJasperServiceAsync;
import muhasebe.dto.MuhJasperDto;
import muhasebe.util.exception.MUHException;
import muhasebe.util.jasper.JasperUtil;

@Slf4j
@Component
public class MuhJasperServiceImpl implements MuhJasperServiceAsync {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public Service service;

	@Autowired
	private JasperUtil util;

	@Override
	public byte[] jasper(String turu, String ekran, String adi, MuhJasperDto params) throws MUHException {
		if (ekran != null && ekran.equals("MUH_KOD"))
			return getKod(turu, adi, params);

		if (ekran != null && ekran.equals("MUH_HESAP"))
			return getHesap(turu, adi, params);

		if (ekran != null && ekran.equals("MUH_BEDEL"))
			return getBedel(turu, adi, params);

		return null;
	}

	public byte[] getKod(String turu, String adi, MuhJasperDto dto) throws MUHException {
		Map<String, Object> parameters = new HashMap<>();
		log.info("Generate Report To " + adi);
		return util.export(turu, adi, parameters);
	}

	public byte[] getHesap(String turu, String adi, MuhJasperDto dto) throws MUHException {
		Map<String, Object> parameters = new HashMap<>();
		log.info("Generate Report To " + adi);
		return util.export(turu, adi, parameters);
	}

	public byte[] getBedel(String turu, String adi, MuhJasperDto dto) throws MUHException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("P_KIRACI", dto.getKiraciId());

		log.info("Generate Report To " + adi);
		return util.export(turu, adi, parameters);
	}

}
