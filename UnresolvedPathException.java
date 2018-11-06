
/**
 * <code>UnresolvedPathException</code> is thrown when the passed in path cannot
 * be be resolved.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class UnresolvedPathException extends Exception {
	private static final long serialVersionUID = -8568120812429473972L;

	/**
	 * Returns an instance of <code>UnresolvedPathException</code>.
	 */
	public UnresolvedPathException() {
		super();
	}

	/**
	 * Returns an instance of <code>UnresolvedPathException</code> along with the
	 * specified message.
	 * 
	 * @param message The message that accompanies the error.
	 */
	public UnresolvedPathException(String message) {
		super(message);
	}
}
