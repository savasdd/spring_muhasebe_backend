package muhasebe.util;

public class EnumUtil {

	// Auth
	public static final String UNAUTHORIZED_ACIKLAMA = "Yetkiniz mevcut değil ! ";
	public static final String CREDENTIALS_MISSING = "Kimlik Bilgileri Eksik !";
	public static final String INVALID_CREDENTIALS = "Yanlış Kimlik Bilgileri !";
	public static final String USER_DISABLED = "Engellenmiş Kullanıcı !";

	// TOKEN
	public static final long TOKEN_EXPIRE_TIME = 5_900_000; // 90000 :2sn
	public static final long TOKEN_REFRESH_EXPIRE_TIME = 5_900_000; // 150000 :2sn
	public static final String SECRET_KEY = "svsd_muhsb_2022?";
	public static final String TOKEN_NOT_CREATED = "Token Oluşturulamadı !";
	public static final String INVALID_TOKEN = "Token Formatı Yanlış !";
	public static final String EXPIRED_TOKEN = "Geçerlilik Tarihi Sona Ermiş Token !";
	public static final String INVALID_TOKEN_REFRESH = "Refresh Token Bulunamadı !";
	public static final String LDAP_NOT_FOUND_USER = "LDAP Kullanıcısı Bulunamadı !";

	// Uyari
	public static final String NOT_CREATE = "Kayıt Hatası Oluştu!";
	public static final String OK_GET = "Successfull retrieved data!";
	public static final String OK_CREATE = "Successfull created data!";
	public static final String OK_UPDATE = "Successfull updated data!";
	public static final String OK_DELETE = "Successfull deleted data!";

}
