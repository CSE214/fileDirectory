package fileDirectory;

/**
 * The <code>DirectoryNode</code> class represents a node in the file tree.
 * 
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class DirectoryNode {
	private final static int MAX_CHILDREN = 10; // Max amount of children per node
	private String name; // Name of the node
	private DirectoryNode[] children; // Array of children
	private String[] childrenNames; // Array of children Names
	private DirectoryNode parent; // The parent of this node
	private int childrenCount; // Number of children
	private boolean isFile; // True if node is file, false otherwise

	/**
	 * @return The name of this instance
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The new name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The children of this instance
	 */
	public DirectoryNode[] getChildren() {
		return children;
	}

	/**
	 * @param children The new children to set
	 */
	public void setChildren(DirectoryNode[] children) {
		this.children = children;
	}

	/**
	 * @return The childrenNames of this instance
	 */
	public String[] getChildrenNames() {
		return childrenNames;
	}

	/**
	 * @param childrenNames The new childrenNames to set
	 */
	public void setChildrenNames(String[] childrenNames) {
		this.childrenNames = childrenNames;
	}

	/**
	 * @return The parent of this instance
	 */
	public DirectoryNode getParent() {
		return parent;
	}

	/**
	 * @param parent The new parent to set
	 */
	public void setParent(DirectoryNode parent) {
		this.parent = parent;
	}

	/**
	 * @return The childrenCount of this instance
	 */
	public int getChildrenCount() {
		return childrenCount;
	}

	/**
	 * @param childrenCount The new childrenCount to set
	 */
	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	/**
	 * @return The isFile of this instance
	 */
	public boolean isFile() {
		return isFile;
	}

	/**
	 * @param isFile The new isFile status to set
	 */
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	/**
	 * Adds the specified node as a child to the first available position in
	 * <code>children</code>.
	 * 
	 * <dl>
	 * <dt>Preconditions:</dt>
	 * <dd>This instance is not a file, and there is room in the node for another
	 * child.</dd>
	 * </dl>
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The node has been added as a child. If full, throws a
	 * FullDirectoryException.</dd>
	 * </dl>
	 * 
	 * @param node The node to add
	 * @throws NotADirectoryException    if this instance of DirectoryNode is a file
	 * @throws FullDirectoryException    if this instance of DirectoryNode is full
	 * @throws ConflictingNamesException if a node of the same type and name already
	 *                                   exists
	 */
	public void addChild(DirectoryNode node)
			throws NotADirectoryException, FullDirectoryException, ConflictingNamesException {
		if (childrenCount == MAX_CHILDREN) {
			throw new FullDirectoryException("No more children can be added to this node.");
		} else if (isFile) {
			throw new NotADirectoryException("A file cannot have children.");
		} else if (getChildIndex(node.getName()) != -1) {
			throw new ConflictingNamesException("You cannot have two files or two directories with the same name.");
		}
		children[childrenCount] = node;
		childrenNames[childrenCount] = node.getName();
		node.setParent(this);
		childrenCount += 1;
	}

	/**
	 * Removes a child of this DirectoryNode instance, if found.
	 * 
	 * @param name The name of the node to remove
	 * @throws UnresolvedPathException if the node doesn't exist
	 * @return The removed node
	 */
	public DirectoryNode removeChild(String name) throws UnresolvedPathException {
		DirectoryNode removedNode = null;
		int index = getChildIndex(name);
		if (index == -1) {
			throw new UnresolvedPathException("That node does not exist.");
		}
		removedNode = children[index];
		for (int i = index + 1; i < childrenCount + 1; i++) {
			children[i - 1] = children[i];
		}
		childrenCount -= 1;
		return removedNode;
	}

	/**
	 * Gets the index of specified child, if it exists.
	 * 
	 * @param name The name of the child
	 * @return The index of the child, or -1 if not found.
	 */
	public int getChildIndex(String name) {
		int index = -1;
		for (int i = 0; i < childrenCount; i++) {
			if (children[i].getName().equals(name)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Returns an instance of DirectoryNode
	 */
	public DirectoryNode() {
		this.children = null;
		this.childrenNames = null;
		this.childrenCount = 0;
		this.parent = null;
	}

	/**
	 * Returns an instance of DirectoryNode
	 * 
	 * @param name   Name of node
	 * @param parent The parent of this node
	 * @param isFile True if node is a file, false otherwise
	 */
	public DirectoryNode(String name, boolean isFile) {
		this();
		this.name = name;
		this.isFile = isFile;
		if (!isFile) {
			this.children = new DirectoryNode[MAX_CHILDREN];
			this.childrenNames = new String[MAX_CHILDREN];
		}
	}

}
