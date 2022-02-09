package muhasebe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import muhasebe.custom.Service;
import muhasebe.dto.MuhJasperDto;
import muhasebe.util.exception.MUHException;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "muhasebe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MuhJasperController {

	@Autowired
	private Service service;

	@Transactional
	@PostMapping(value = "/rapors")
	public byte[] kodPdf(@RequestParam(value = "turu") String raporTuru,
			@RequestParam(value = "ekran", required = false) String ekran,
			@RequestParam(value = "adi", required = false) String raporAdi, @RequestBody @Validated MuhJasperDto params)
			throws MUHException {
		return service.getJasper().jasper(raporTuru, ekran, raporAdi, params);
	}

}
