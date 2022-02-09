package muhasebe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import muhasebe.custom.Service;
import muhasebe.dto.Generic;
import muhasebe.dto.MuhKodDto;
import muhasebe.model.MuhKod;
import muhasebe.util.EnumUtil;
import muhasebe.util.aop.ElasticLog;
import muhasebe.util.exception.MUHException;
import muhasebe.util.exception.MUHExceptionValidation;
import muhasebe.util.response.ResponseHandler;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "muhasebe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MuhKodController {

	@Autowired
	private Service service;

	@Validated
	@GetMapping(value = "/kods")
	public Generic<MuhKodDto> getKods(@RequestParam(value = "search", required = false) String search, Pageable page)
			throws MUHException {
		return service.getKod().getKods(search, page);
	}

	@ElasticLog
	@Validated
	@GetMapping(value = "/kods/{id}")
	public ResponseEntity<Object> getKodById(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getKod().getKodById(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@Validated
	@GetMapping(value = "/kods/ust/{id}")
	public ResponseEntity<Object> getKodByUstId(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getKod().getKodByUstId(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PostMapping(value = "/kods")
	public ResponseEntity<Object> createKod(@RequestBody @Validated MuhKod model, BindingResult errors)
			throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_CREATE, HttpStatus.CREATED,
					service.getKod().createKod(model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Validated
	@Transactional
	@PutMapping(value = "/kods/{id}")
	public ResponseEntity<Object> updateKod(@PathVariable String id, @RequestBody MuhKod model, BindingResult errors)
			throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_UPDATE, HttpStatus.OK,
					service.getKod().updateKod(id, model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Validated
	@Transactional
	@DeleteMapping(value = "/kods/{id}")
	public ResponseEntity<Object> deleteKod(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_DELETE, HttpStatus.OK, service.getKod().deleteKod(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

}
