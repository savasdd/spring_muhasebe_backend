package muhasebe.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import muhasebe.custom.Service;
import muhasebe.dto.MuhKullaniciDto;
import muhasebe.model.MuhKullaniciLog;
import muhasebe.model.MuhRefreshToken;
import muhasebe.security.TokenManager;
import muhasebe.security.request.LoginRequest;
import muhasebe.security.request.RefreshTokenRequest;
import muhasebe.security.response.TokenRefreshResponse;
import muhasebe.security.response.TokenResponse;
import muhasebe.security.services.UserDetailsImpl;
import muhasebe.util.EnumUtil;
import muhasebe.util.exception.MUHException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	@Autowired
	private AuthenticationManager aumanager;

	@Autowired
	private TokenManager manager;

	@Autowired
	private Service service;

	@Transactional
	@PostMapping(value = "/getToken")
	public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) throws MUHException {

		byte[] decodePass = Base64.getDecoder().decode(request.getPassword());
		request.setPassword(new String(decodePass));

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		Authentication authentication = aumanager.authenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String jwt = manager.generateJwtToken(userDetails);
		MuhRefreshToken refreshToken = service.getRefresh().createRefreshToken(userDetails.getUsername());
		TokenResponse response = new TokenResponse(jwt, refreshToken.getToken(), userDetails.getUsername());
		System.err.println("Generate Token For: " + request.getUsername());
		createLog(userDetails.getUsername(), jwt);

		return ResponseEntity.ok().body(response);
	}

	@Operation(hidden = true)
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) throws MUHException {
		try {
			String requestRefreshToken = request.getRefreshToken();

			MuhRefreshToken tokens = service.getRefresh()
					.verifyExpiration(service.getRefresh().findByToken(requestRefreshToken).get());
			String token = manager.generateToken(tokens.getKullaniciAdi());
			System.err.println("Generate Refresh Token For: " + tokens.getKullaniciAdi());
			createLog(tokens.getKullaniciAdi(), token);

			return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
		} catch (Exception e) {
			throw new MUHException(EnumUtil.INVALID_TOKEN_REFRESH);
		}
	}

	public void createLog(String username, String token) throws MUHException {
		MuhKullaniciLog log = new MuhKullaniciLog();
		log.setKullaniciAdi(username);
		log.setCreateDate(new Date());
		log.setExpireDate(new Date(System.currentTimeMillis() + EnumUtil.TOKEN_EXPIRE_TIME));
		service.getLog().createKullanici(log);
	}

	public static LocalDateTime getTime(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
