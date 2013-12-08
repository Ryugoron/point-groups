package pointGroups.util.polymake;

public class PolymakeException extends RuntimeException {
	private static final long serialVersionUID = 3796180588091149932L;

	public final static String CANNOT_START = "Polymake process could not be started.";

	public PolymakeException(String whatHappend) {
		super(whatHappend);
	}
}
