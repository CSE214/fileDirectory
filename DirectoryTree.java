package fileDirectory;

/**
 * The <code>DirectoryTree</code> class models a directory system using trees.
 * 
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class DirectoryTree {
	private DirectoryNode root; // The root of the system
	private DirectoryNode cursor; // A pointer that can move throughout the tree
	private String workingDirectory; // A string representing the current working directory

	/**
	 * @return The root of this instance
	 */
	public DirectoryNode getRoot() {
		return root;
	}

	/**
	 * @param root The new root to set
	 */
	public void setRoot(DirectoryNode root) {
		this.root = root;
	}

	/**
	 * @return The cursor of this instance
	 */
	public DirectoryNode getCursor() {
		return cursor;
	}

	/**
	 * @param cursor The new cursor to set
	 */
	public void setCursor(DirectoryNode cursor) {
		this.cursor = cursor;
		workingDirectory = "root";
	}

	/**
	 * Returns an instance of DirectoryTree
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The tree contains a single DirectoryNode named "root", and both cursor
	 * and root reference this node</dd>
	 * </dl>
	 */
	public DirectoryTree() {
		root = new DirectoryNode("root", true);
		cursor = root;
		workingDirectory = "root";
	}

	/**
	 * Moves the cursor to the root of the system.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The cursor now points to the root</dd>
	 * </dl>
	 */
	public void resetCursor() {
		cursor = root;
	}

	/**
	 * Moves the cursor to the specified child.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The cursor now points to the appropriate child. If the name was not a
	 * directory, a NotADirectoryException hs been thrown</dd>
	 * </dl>
	 * 
	 * @param name The name of the node to go to
	 * @throws NotADirectoryException  If the current cursor is not a directory
	 * @throws UnresolvedPathException If the child cannot be found
	 * 
	 */
	public void goToChild(String name) throws NotADirectoryException, UnresolvedPathException {
		int index = cursor.getChildIndex(name);
		if (index != -1) {
			cursor = cursor.getChildren()[index];
		} else {
			throw new UnresolvedPathException("There is no such child.");
		}
	}

	/**
	 * Moves the cursor through the specified path
	 * 
	 * @param path The path to move through
	 * @throws NotADirectoryException  If the cursor is at file
	 * @throws UnresolvedPathException If the path is invalid
	 */
	public void changeDirectory(String path) throws NotADirectoryException, UnresolvedPathException {
		String[] pathArray = path.split("/");
		for (int i = 0; i < pathArray.length; i++) {
			goToChild(pathArray[i]);
		}
	}
}
