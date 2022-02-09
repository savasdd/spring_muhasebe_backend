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
import muhasebe.dto.MuhRolDto;
import muhasebe.model.MuhRol;
import muhasebe.util.EnumUtil;
import muhasebe.util.aop.ElasticLog;
import muhasebe.util.exception.MUHException;
import muhasebe.util.exception.MUHExceptionValidation;
import muhasebe.util.response.ResponseHandler;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "muhasebe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MuhRolController {

	@Autowired
	private Service service;

	@Validated
	@GetMapping(value = "/rols")
	public Generic<MuhRolDto> getRols(@RequestParam(value = "search", required = false) String search, Pageable page)
			throws MUHException {
		return service.getRol().getRols(search, page);
	}

	@ElasticLog
	@Validated
	@GetMapping(value = "/rols/{id}")
	public ResponseEntity<Object> getRolById(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getRol().getRolById(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PostMapping(value = "/rols")
	public ResponseEntity<Object> createRol(@RequestBody @Validated MuhRol model, BindingResult errors)
			throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_CREATE, HttpStatus.CREATED,
					service.getRol().createRol(model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@PutMapping(value = "/rols/{id}")
	public ResponseEntity<Object> updateRol(@PathVariable String id, @RequestBody @Validated MuhRol model,
			BindingResult errors) throws MUHException {
		try {
			if (errors.hasErrors())
				throw new MUHExceptionValidation(errors);

			return ResponseHandler.generateResponse(EnumUtil.OK_UPDATE, HttpStatus.OK,
					service.getRol().updateRol(id, model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@ElasticLog
	@Transactional
	@DeleteMapping(value = "/rols/{id}")
	public ResponseEntity<Object> deleteRol(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_DELETE, HttpStatus.OK, service.getRol().deleteRol(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

}
