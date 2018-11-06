
/**
 * <code>ConflictingNamesException</code> is thrown when there is an attempt to
 * make two nodes of the same type and name.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class ConflictingNamesException extends Exception {
	private static final long serialVersionUID = -8568120812429473972L;

	/**
	 * Returns an instance of <code>ConflictingNamesException</code>.
	 */
	public ConflictingNamesException() {
		super();
	}

	/**
	 * Returns an instance of <code>ConflictingNamesException</code> along with the
	 * specified message.
	 * 
	 * @param message The message that accompanies the error.
	 */
	public ConflictingNamesException(String message) {
		super(message);
	}
}