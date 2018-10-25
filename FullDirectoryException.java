package fileDirectory;

/**
 * <code>FullDirectoryException</code> is thrown when the current
 * <code>DirectoryNode</code> is full and cannot have more children.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class FullDirectoryException extends Exception {
	private static final long serialVersionUID = -8568120812429473972L;

	/**
	 * Returns an instance of <code>FullDirectoryException</code>.
	 */
	public FullDirectoryException() {
		super();
	}

	/**
	 * Returns an instance of <code>FullDirectoryException</code> along with the
	 * specified message.
	 * 
	 * @param message The message that accompanies the error.
	 */
	public FullDirectoryException(String message) {
		super(message);
	}
}
