
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
	 * @throws NotADirectoryException  If the target is not a directory
	 * @throws UnresolvedPathException If the child cannot be found
	 * 
	 */
	public void goToChild(String name) throws NotADirectoryException, UnresolvedPathException {
		int index = cursor.getChildIndex(name);
		if (index != -1) {
			if (cursor.getChildren()[index].isFile()) {
				throw new NotADirectoryException("You cannot move to a file.");
			}
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
		DirectoryNode originalCursor = cursor;
		for (int i = 0; i < pathArray.length; i++) {
			try {
				goToChild(pathArray[i]);
			} catch (UnresolvedPathException e) {
				cursor = originalCursor;
				throw new UnresolvedPathException("The path is invalid.");
			} catch (NotADirectoryException e) {
				cursor = originalCursor;
				throw new NotADirectoryException("You cannot move to a file.");
			}
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
	 * @throws IllegalArgumentException  If the name contains any white space or '/'
	 *                                   characters.
	 * @throws FullDirectoryException    If the cursor does not have more room for a
	 *                                   directory.
	 * @throws ConflictingNamesException If a directory with the same name already
	 *                                   exists
	 */
	public void makeDirectory(String name)
			throws IllegalArgumentException, FullDirectoryException, NotADirectoryException, ConflictingNamesException {
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
	 * @throws IllegalArgumentException  If the name contains any white space or '/'
	 *                                   characters.
	 * @throws FullDirectoryException    If the cursor does not have more room for a
	 *                                   file.
	 * @throws ConflictingNamesException If a file with the same name already exists
	 */
	public void makeFile(String name)
			throws IllegalArgumentException, FullDirectoryException, NotADirectoryException, ConflictingNamesException {
		name = name.trim();
		if (name.indexOf("/") != -1 || name.indexOf(" ") != -1) {
			throw new IllegalArgumentException("File name should not have whitespace or '/' characters.");
		}
		cursor.addChild(new DirectoryNode(name, true));
	}

	/**
	 * Moves the node located at sourcePath to targetPath
	 * 
	 * @param sourcePath The source of the node
	 * @param targetPath The destination of the node
	 * 
	 * @throws NotADirectoryException    If the targetPath does not resolve to a
	 *                                   directory.
	 * @throws UnresolvedPathException   If the sourcePath or targetPath are
	 *                                   invalid.
	 * @throws FullDirectoryException    If the directory at targetPath is already
	 *                                   full.
	 * @throws ConflictingNamesException If a node of the same type and name already
	 *                                   exists
	 */
	public void moveDirectory(String sourcePath, String targetPath)
			throws NotADirectoryException, UnresolvedPathException, FullDirectoryException, ConflictingNamesException {
		DirectoryNode originalNode = cursor;
		String originalWorkingDirectory = workingDirectory;
		DirectoryNode removedNode = null;
		// Get node to move
		String[] sourcePathArray = sourcePath.split("/");
		String sourcePathNode = sourcePathArray[sourcePathArray.length - 1];
		sourcePathArray[sourcePathArray.length - 1] = "";
		try {
			// Go to node's parent and remove it
			changeDirectory(String.join("/", sourcePathArray));
			removedNode = cursor.removeChild(sourcePathNode);
			// Go to targetPath and add node as child
			cursor = originalNode;
			changeDirectory(targetPath);
			cursor.addChild(removedNode);
			// Go back to original position
			cursor = originalNode;
			workingDirectory = originalWorkingDirectory;
		} catch (Exception e) {
			// Go back to original position
			cursor = originalNode;
			workingDirectory = originalWorkingDirectory;
			// If node was removed while trying, restore node
			changeDirectory(String.join("/", sourcePathArray));
			cursor.addChild(removedNode);
			// Go back to original position
			cursor = originalNode;
			workingDirectory = originalWorkingDirectory;
			// Handle errors
			if (e instanceof NotADirectoryException) {
				throw new NotADirectoryException("The target path does not resolve to a directory.");
			} else if (e instanceof UnresolvedPathException) {
				throw new UnresolvedPathException("The target or source path is invalid");
			} else if (e instanceof FullDirectoryException) {
				throw new FullDirectoryException("The directory at the target path is full.");
			} else if (e instanceof ConflictingNamesException) {
				throw new ConflictingNamesException("You cannot have two nodes of the same type and name.");
			}

		}
	}

	/**
	 * Substep in recursion for the find method.
	 * 
	 * @param name        Name of the desired file
	 * @param currentNode The current node in the tree traversal
	 * @param currentPath The current path in the tree traversal
	 * @return The path if a match was found, else return the empty string
	 */
	public String find(String name, DirectoryNode currentNode, String currentPath) {
		// Check if current node has a match
		if (name.equals(currentNode.getName()))
			return currentPath;
		// Check if any children node has a match
		DirectoryNode[] children = currentNode.getChildren();
		for (int i = 0; i < currentNode.getChildrenCount(); i++) {
			String result = find(name, children[i], currentPath + "/" + children[i].getName());
			if (!result.equals(""))
				return result;
		}
		// Else, search failed for this tree: return empty string
		return "";
	}

	/**
	 * Substep in recursion for the printAllFound method.
	 * 
	 * @param name        Name of the desired file
	 * @param currentNode The current node in the tree traversal
	 * @param currentPath The current path in the tree traversal
	 * @return The path if a match was found, else return the empty string
	 */
	public void printAllFound(String name, DirectoryNode currentNode, String currentPath) {
		// Check if current node has a match
		if (name.equals(currentNode.getName()))
			System.out.println(currentPath);
		// Check if any children node has a match
		DirectoryNode[] children = currentNode.getChildren();
		for (int i = 0; i < currentNode.getChildrenCount(); i++) {
			printAllFound(name, children[i], currentPath + "/" + children[i].getName());
		}
	}

	/**
	 * Prints all paths that resolve to a file with the specified name.
	 * 
	 * @param name Name of the desired file
	 */
	public void printAllFound(String name) {
		printAllFound(name, root, "root");
	}

	/**
	 * Returns a string containing a path to the specified directory.
	 */
	public String find(String name) {
		return find(name, root, "root");
	}

	/**
	 * Prints out the directory tree of the current node using recursive pre-order
	 * tree traversal.
	 * 
	 * @param node  The node to start from.
	 * @param depth The depth of the node.
	 */
	public void printDirectoryTree(DirectoryNode node, int depth) {
		// Print out the name of current node
		String indents = "";
		for (int i = 0; i < depth; i++) {
			indents += "    ";
		}
		System.out.println(indents + (node.isFile() ? " " : "|") + "- " + node.getName());
		if (!node.isFile()) {
			// Advance to children nodes
			DirectoryNode[] children = node.getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null)
					printDirectoryTree(children[i], depth + 1);
			}
		}
	}

	/**
	 * Prints out the directory tree that has the current cursor as its root.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The tree starting from the current path has been printed to the
	 * user.</dd>
	 * </dl>
	 */
	public void printDirectoryTree() {
		printDirectoryTree(cursor, 0);
	}

	/**
	 * Prints out the entire directory tree.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The tree starting from the root has been printed to the user.</dd>
	 * </dl>
	 */
	public void printDirectoryTreeFromRoot() {
		printDirectoryTree(root, 0);
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
