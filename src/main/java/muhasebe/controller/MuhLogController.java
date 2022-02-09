package muhasebe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import muhasebe.model.MuhKullaniciLog;
import muhasebe.util.EnumUtil;
import muhasebe.util.exception.MUHException;
import muhasebe.util.response.ResponseHandler;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "muhasebe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MuhLogController {

	@Autowired
	private Service service;

	@Validated
	@GetMapping(value = "/logs/kullanicis")
	public ResponseEntity<Object> getKullaniciLog() throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getLog().getKullanici());
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@Validated
	@GetMapping(value = "/logs/hatas")
	public ResponseEntity<Object> getHataLog() throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getLog().getHata());
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@Validated
	@GetMapping(value = "/logs/islems")
	public ResponseEntity<Object> getIslemLog() throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getLog().getIslem());
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@PostMapping(value = "/logs/kullanicis")
	public ResponseEntity<Object> createKullaniciLog(@RequestBody @Validated MuhKullaniciLog model)
			throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_CREATE, HttpStatus.CREATED,
					service.getLog().createKullanici(model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}

	}

	@PutMapping(value = "/logs/kullanicis/{id}")
	public ResponseEntity<Object> updateKullaniciLog(@PathVariable String id,
			@RequestBody @Validated MuhKullaniciLog model) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_UPDATE, HttpStatus.OK,
					service.getLog().updateKullanici(id, model));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@DeleteMapping(value = "/logs/kullanicis/{id}")
	public ResponseEntity<Object> deleteKullaniciLog(@PathVariable String id) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_DELETE, HttpStatus.OK,
					service.getLog().deleteKullanici(id));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	// Ek İşlemler
	@Validated
	@GetMapping(value = "/logs/search")
	public ResponseEntity<Object> kullaniciSearch(@RequestParam("search") String search) throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK,
					service.getLog().kullaniciSearch(search));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@Validated
	@GetMapping(value = "/logs/native")
	public ResponseEntity<Object> nativeQuery() throws MUHException {
		try {
			return ResponseHandler.generateResponse(EnumUtil.OK_GET, HttpStatus.OK, service.getLog().getNativeQuery());
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@Validated
	@GetMapping(value = "/logs/string")
	public ResponseEntity<MuhKullaniciLog> stringQuery() throws MUHException {
		return new ResponseEntity<MuhKullaniciLog>(service.getLog().getStringQuery(), HttpStatus.OK);
	}

	@Validated
	@GetMapping(value = "/logs/criteria")
	public ResponseEntity<MuhKullaniciLog> criteriaQuery() throws MUHException {
		return new ResponseEntity<MuhKullaniciLog>(service.getLog().getCriteriaQuery(), HttpStatus.OK);
	}
}
