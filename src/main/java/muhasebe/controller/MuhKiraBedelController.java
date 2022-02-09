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
import muhasebe.dto.MuhKiraBedelDto;
import muhasebe.model.MuhKiraBedel;
import muhasebe.util.EnumUtil;
import muhasebe.util.aop.ElasticLog;
import muhasebe.util.exception.MUHException;
import muhasebe.util.exception.MUHExceptionValidation;
import muhasebe.util.response.ResponseHandler;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "muhasebe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MuhKiraBedelController {

	@Autowired
	private Service service;

	@Validated
	@GetMapping(value = "/bedels")
	public Generic<MuhKiraBedelDto> getBedels(@RequestParam(value = "search", required = false) String search,
			Pageable page) throws MUHException {
		return service.getBedel().getBedels(search, page);
	}

	@ElasticLog
	@Validated
	@GetMapping(value = "/kiracis/{kiraciId}/bedels/{id}")
	public ResponseEntity<Object> getBedelById(@PathVariable("kiraciId") String kiraciId, @PathVariable("id") String id)
			throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK,
					service.getBedel().getBedelById(kiraciId, id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PostMapping(value = "/kiracis/{kiraciId}/bedels")
	public ResponseEntity<Object> createBedel(@PathVariable("kiraciId") String kiraciId,
			@RequestBody @Validated MuhKiraBedel model, BindingResult errors) throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_CREATE, HttpStatus.CREATED,
					service.getBedel().createBedel(kiraciId, model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PutMapping(value = "/kiracis/{kiraciId}/bedels/{id}")
	public ResponseEntity<Object> updateBedel(@PathVariable("kiraciId") String kiraciId, @PathVariable("id") String id,
			@RequestBody @Validated MuhKiraBedel model, BindingResult errors) throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_UPDATE, HttpStatus.OK,
					service.getBedel().updateBedel(kiraciId, id, model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}

	}

	@ElasticLog
	@Transactional
	@DeleteMapping(value = "/kiracis/{kiraciId}/bedels/{id}")
	public ResponseEntity<Object> deleteBedel(@PathVariable("kiraciId") String kiraciId, @PathVariable("id") String id)
			throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_DELETE, HttpStatus.OK,
					service.getBedel().deleteBedel(kiraciId, id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
}
