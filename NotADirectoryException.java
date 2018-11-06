
/**
 * <code>NotADirectoryException</code> is thrown when the user attempts to move
 * to node that is not a directory.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class NotADirectoryException extends Exception {
	private static final long serialVersionUID = -8568120812429473972L;

	/**
	 * Returns an instance of <code>NotADirectoryException</code>.
	 */
	public NotADirectoryException() {
		super();
	}

	/**
	 * Returns an instance of <code>NotADirectoryException</code> along with the
	 * specified message.
	 * 
	 * @param message The message that accompanies the error.
	 */
	public NotADirectoryException(String message) {
		super(message);
	}
}
