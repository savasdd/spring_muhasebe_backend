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
import muhasebe.dto.MuhOranDto;
import muhasebe.model.MuhOran;
import muhasebe.util.EnumUtil;
import muhasebe.util.aop.ElasticLog;
import muhasebe.util.exception.MUHException;
import muhasebe.util.exception.MUHExceptionValidation;
import muhasebe.util.response.ResponseHandler;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "muhasebe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MuhOranController {

	@Autowired
	private Service service;

	@Validated
	@GetMapping(value = "/orans")
	public Generic<MuhOranDto> getOrans(@RequestParam(value = "search", required = false) String search, Pageable page)
			throws MUHException {
		return service.getOran().getOrans(search, page);
	}

	@ElasticLog
	@Validated
	@GetMapping(value = "/orans/{id}")
	public ResponseEntity<Object> getOranById(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getOran().getOranById(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PostMapping(value = "/orans")
	public ResponseEntity<Object> createOran(@RequestBody @Validated MuhOran model, BindingResult errors)
			throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_CREATE, HttpStatus.CREATED,
					service.getOran().createOran(model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PutMapping(value = "/orans/{id}")
	public ResponseEntity<Object> updateOran(@PathVariable String id, @RequestBody @Validated MuhOran model,
			BindingResult errors) throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_UPDATE, HttpStatus.OK,
					service.getOran().updateOran(id, model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@DeleteMapping(value = "/orans/{id}")
	public ResponseEntity<Object> deleteOran(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_DELETE, HttpStatus.OK,
					service.getOran().deleteOran(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

}
