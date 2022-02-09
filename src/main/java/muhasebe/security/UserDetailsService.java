package muhasebe.security;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import muhasebe.dto.MuhKullaniciDto;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private muhasebe.custom.Service service;

	@PostConstruct
	public void init() {
		// Todo
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MuhKullaniciDto user = service.getKullanici().findKullaniciAdi(username);

		if (user.getKullaniciAdi().contains(username))
			return new User(username, user.getSifre(), new ArrayList<>());

		throw new UsernameNotFoundException(username);

	}

}
