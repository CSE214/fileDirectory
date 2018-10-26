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
	 * @return The workingDirectory of this instance
	 */
	public String presentWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * @param workingDirectory The new workingDirectory to set
	 */
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
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
		root = new DirectoryNode("root", false);
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
		workingDirectory = "root";
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
			workingDirectory += "/" + cursor.getName();
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

	/**
	 * Moves the cursor up to its parent directory. If the cursor is at the root,
	 * this does nothing.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The cursor now points to the parent directory, if it exists. The working
	 * directory has been updated.</dd>
	 * </dl>
	 */
	public void goToParent() {
		cursor = cursor.getParent();
		workingDirectory = workingDirectory.replaceAll("/[A-Za-z0-9]*$", "");
	}

	/**
	 * Creates a new directory with the specified name.
	 * 
	 * <dl>
	 * <dt>Preconditions:</dt>
	 * <dd>'name' is a legal argument (does not contain spaces " " or forward
	 * slashes "/")</dd>
	 * </dl>
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>A new directory has been added to the children of the cursor, or an
	 * exception has been thrown.</dd>
	 * </dl>
	 * 
	 * @param name The name of the new directory
	 * @throws IllegalArgumentException If the name contains any white space or '/'
	 *                                  characters.
	 * @throws FullDirectoryException   If the cursor does not have more room for a
	 *                                  directory.
	 * @throws NotADirectoryException   If the cursor is not a directory.
	 */
	public void makeDirectory(String name)
			throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
		name = name.trim();
		if (name.indexOf("/") != -1 || name.indexOf(" ") != -1) {
			throw new IllegalArgumentException("File name should not have whitespace or '/' characters.");
		}
		cursor.addChild(new DirectoryNode(name, false));
	}

	/**
	 * Creates a new file with the specified name.
	 * 
	 * <dl>
	 * <dt>Preconditions:</dt>
	 * <dd>'name' is a legal argument (does not contain spaces " " or forward
	 * slashes "/")</dd>
	 * </dl>
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>A new file has been added to the children of the cursor, or an exception
	 * has been thrown.</dd>
	 * </dl>
	 * 
	 * @param name The name of the new file
	 * @throws IllegalArgumentException If the name contains any white space or '/'
	 *                                  characters.
	 * @throws FullDirectoryException   If the cursor does not have more room for a
	 *                                  file.
	 * @throws NotADirectoryException   If the cursor is not a directory.
	 */
	public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
		name = name.trim();
		if (name.indexOf("/") != -1 || name.indexOf(" ") != -1) {
			throw new IllegalArgumentException("File name should not have whitespace or '/' characters.");
		}
		cursor.addChild(new DirectoryNode(name, true));
	}

	/**
	 * Returns a string listing all the children of the cursor. Names are separated
	 * by spaces " ".
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The cursor remains at its original location.</dd>
	 * </dl>
	 * 
	 * @returns A formatted string of directory names
	 */
	public String listDirectory() {
		return String.join(" ", cursor.getChildrenNames()).replaceAll(" null", "");
	}
}
