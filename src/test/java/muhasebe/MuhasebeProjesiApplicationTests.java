package muhasebe;

import muhasebe.custom.Service;
import muhasebe.model.MuhHesap;
import muhasebe.util.exception.MUHException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MuhasebeProjesiApplicationTests {

    /**
     * test dizini altına resources eklenerek test için oluşturulan db bağlanılır.
     * bu sayede gerçek ortamdan farklı olarak çalışabiliriz.
     */

    @Autowired
    private Service service;

    //@Test
    void contextLoads() throws MUHException {
        MuhHesap hesap = new MuhHesap();
        hesap.setHesapNo("125487");
        hesap.setAktif(true);
        hesap.setKod(null);

        service.getHesap().createHesap(hesap);
    }

}
