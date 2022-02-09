package muhasebe.util.rsql.argument;

public class AFormatException extends RuntimeException {
	/*
	 * query içinde gönderilen parametrelere göre uymayan veri tipleri için hata
	 * döndüren sınıftır. çalışma zamanına göre hata vermektedir
	 */

	private static final long serialVersionUID = 521849874508654920L;

	private final String argument;
	private final Class<?> propertyType;

	public AFormatException(String argument, Class<?> propertyType) {
		super("Cannot cast '" + argument + "' to type " + propertyType);
		this.argument = argument;
		this.propertyType = propertyType;
	}

	public String getArgument() {
		return argument;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

}