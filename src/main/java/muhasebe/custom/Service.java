package muhasebe.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muhasebe.custom.async.MuhHesapServiceAsync;
import muhasebe.custom.async.MuhJasperServiceAsync;
import muhasebe.custom.async.MuhKiraBedelServiceAsync;
import muhasebe.custom.async.MuhKiraciServiceAsync;
import muhasebe.custom.async.MuhKodServiceAsync;
import muhasebe.custom.async.MuhKullaniciServiceAsync;
import muhasebe.custom.async.MuhLogServiceAsync;
import muhasebe.custom.async.MuhOranServiceAsync;
import muhasebe.custom.async.MuhRolServiceAsync;
import muhasebe.custom.async.MuhTokenRefreshServiceAsync;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Service {

	@Autowired
	private MuhKodServiceAsync kod;

	@Autowired
	private MuhKiraciServiceAsync kiraci;

	@Autowired
	private MuhOranServiceAsync oran;

	@Autowired
	private MuhKullaniciServiceAsync kullanici;

	@Autowired
	private MuhRolServiceAsync rol;

	@Autowired
	private MuhHesapServiceAsync hesap;

	@Autowired
	private MuhKiraBedelServiceAsync bedel;

	@Autowired
	private MuhLogServiceAsync log;

	@Autowired
	private MuhJasperServiceAsync jasper;

	@Autowired
	private MuhTokenRefreshServiceAsync refresh;

}
