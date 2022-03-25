package muhasebe.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import muhasebe.model.MuhKullanici;
import muhasebe.repository.MuhKullaniciRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MuhKullaniciRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MuhKullanici user = userRepository.findByKullaniciAdi(username)
				.orElseThrow(() -> new UsernameNotFoundException("Kullanıcı Bulunamadı: " + username));

		return UserDetailsImpl.build(user);
	}

}