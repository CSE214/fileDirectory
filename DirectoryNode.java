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
	 * @throws UnresolvedPathException if this instance of DirectoryNode is a file
	 * @throws NotADirectoryException if this instance of DirectoryNode is full
	 */
	public void addChild(DirectoryNode node) throws UnresolvedPathException, NotADirectoryException {
		if (childrenCount == MAX_CHILDREN) {
			throw new UnresolvedPathException("No more children can be added to this node.");
		} else if (isFile) {
			throw new NotADirectoryException("A file cannot have children.");
		}
		children[childrenCount] = node;
		childrenCount += 1;
	}

	/**
	 * Gets the index of specified child, if it exists.
	 * 
	 * @param name The name of the child
	 * @throws NotADirectoryException if this instance of DirectoryNode is a file
	 * @return The index of the child, or -1 if not found.
	 */
	public int getChildIndex(String name) throws NotADirectoryException {
		if (isFile) {
			throw new NotADirectoryException("A file cannot have children.");
		}
		int index = -1;
		for (int i = 0; i < children.length; i++) {
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
		this.children = new DirectoryNode[MAX_CHILDREN];
		this.childrenCount = 0;
	}

	/**
	 * Returns an instance of DirectoryNode
	 * 
	 * @param name   Name of node
	 * @param isFile True if node is a file, false otherwise
	 */
	public DirectoryNode(String name, boolean isFile) {
		this();
		this.name = name;
		this.isFile = isFile;
	}

}
