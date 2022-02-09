package muhasebe.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import muhasebe.custom.Service;
import muhasebe.dto.auth.LoginDto;
import muhasebe.dto.auth.TokenDto;
import muhasebe.model.MuhKullaniciLog;
import muhasebe.security.TokenManager;
import muhasebe.util.exception.MUHException;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	@Autowired
	private AuthenticationManager aumanager;

	@Autowired
	private TokenManager manager;

	@Autowired
	private Service service;

	@Transactional
	@PostMapping(value = "/auth/token")
	public ResponseEntity<TokenDto> login(@RequestBody LoginDto request) throws MUHException {
		try {

			byte[] decodePass = Base64.getDecoder().decode(request.getPassword());
			request.setPassword(new String(decodePass));

			UsernamePasswordAuthenticationToken auth = null;
			auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

			aumanager.authenticate(auth);
			// log.info("Generate Token For: " + request.getUsername());
			System.err.println("Generate Token For: " + request.getUsername());
			String token = manager.generateToken(request.getUsername());
			TokenDto tokenDto = TokenDto.builder().user(request.getUsername()).token(token).build();

			// Log
			MuhKullaniciLog log = new MuhKullaniciLog();
			log.setKullaniciAdi(request.getUsername());
			log.setCreateDate(new Date());
			log.setExpireDate(new Date(System.currentTimeMillis() + 5_900_000));
			service.getLog().createKullanici(log);

			return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static LocalDateTime getTime(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
