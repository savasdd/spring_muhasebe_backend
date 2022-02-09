package muhasebe.util.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import muhasebe.custom.Service;
import muhasebe.model.MuhHataLog;
import muhasebe.util.AuthUtil;

@ControllerAdvice
public class MUHGlobalException extends ResponseEntityExceptionHandler {
	/*
	 * springin @ControllerAdvice özelliği sayesinde custom hatalar handle
	 * edilibiliyor.
	 */

	@Autowired
	private Service service;

	@Autowired
	private AuthUtil util;

	@ExceptionHandler(MUHException.class)
	public ResponseEntity<?> kayitBulunamadi(MUHException ex, WebRequest request) throws MUHException {
		Object hata = ex.getStackTrace();
		MUHExceptionDetails detay = new MUHExceptionDetails(getTime(new Date()), ex.getMessage(),
				request.getDescription(true), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(detay, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalHata(Exception ex, WebRequest request) throws MUHException {
		Object hata = ex.getStackTrace();
		MUHExceptionDetails detay = new MUHExceptionDetails(getTime(new Date()), "Hata: " + ex.getMessage(),
				request.getDescription(true), HttpStatus.INTERNAL_SERVER_ERROR.value());

		// Log
		MuhHataLog log = new MuhHataLog();
		log.setCreateDate(new Date());
		log.setMesagge(ex.getMessage());
		log.setDetails(request.getDescription(true));
		log.setKullaniciAdi((util.getUser().getKullaniciAdi() != null ? util.getUser().getKullaniciAdi() : null));
		log.setStatus((long) HttpStatus.INTERNAL_SERVER_ERROR.value());
		service.getLog().createHata(log);

		return new ResponseEntity<>(detay, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static LocalDateTime getTime(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
