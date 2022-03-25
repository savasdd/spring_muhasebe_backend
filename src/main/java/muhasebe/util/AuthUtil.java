package muhasebe.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import muhasebe.custom.Service;
import muhasebe.dto.MuhKullaniciDto;
import muhasebe.security.services.UserDetailsImpl;

@Component
public class AuthUtil {

	@Autowired
	private Service service;

	public MuhKullaniciDto getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		MuhKullaniciDto kullanici = service.getKullanici().findKullaniciAdi(userDetails.getUsername());

		return kullanici;
	}

}
