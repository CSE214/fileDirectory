
import java.util.Scanner;

/**
 * The <code>BashTerminal</code> class gives the user an interface to interact
 * with the file tree simulation.
 * 
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class BashTerminal {
	private static final String PRE_COMMAND_STRING = "[110261379@hireMe]: $ ";

	private static DirectoryTree directoryTree;
	private static Scanner in;

	/**
	 * Gets input from the user.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The entered command is parsed by the program.</dd>
	 * </dl>
	 */
	private static void getUserInput() {
		System.out.print(PRE_COMMAND_STRING);
		String command = in.nextLine();
		parseCommand(command);
	}

	/**
	 * Prints the user's working directory.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The working directory is printed, and the user is prompted for another
	 * command.</dd>
	 * </dl>
	 */
	private static void printWorkingDirectory() {
		System.out.println(directoryTree.presentWorkingDirectory());
	}

	/**
	 * Prints the associated tree with the current path as root.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The tree is printed, and the user is prompted for another command.</dd>
	 * </dl>
	 */
	private static void listDirectory() {
		directoryTree.printDirectoryTree();
		getUserInput();
	}

	/**
	 * Creates a file at the current working directory with the given name, if
	 * possible.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The file is made if possible, and the user is prompted for another
	 * command.</dd>
	 * </dl>
	 */
	private static void makeFile(String name) {
		try {
			directoryTree.makeFile(name);
		} catch (NotADirectoryException e) {
			System.out.println("ERROR: You can only add files to a directory.");
		} catch (FullDirectoryException e) {
			System.out.println("ERROR: This directory is already full.");
		} catch (IllegalArgumentException e) {
			System.out.println("ERROR: That is not a valid file name.");
		} catch (ConflictingNamesException e) {
			System.out.println("ERROR: There cannot be two directories or two files with the same name.");
		}
	}

	/**
	 * Creates a directory at the current working directory with the given name, if
	 * possible.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The directory is made if possible, and the user is prompted for another
	 * command.</dd>
	 * </dl>
	 */
	private static void makeDirectory(String name) {
		try {
			directoryTree.makeDirectory(name);
		} catch (NotADirectoryException e) {
			System.out.println("ERROR: You can only add files to a directory.");
		} catch (FullDirectoryException e) {
			System.out.println("ERROR: This directory is already full.");
		} catch (IllegalArgumentException e) {
			System.out.println("ERROR: That is not a valid file name.");
		} catch (ConflictingNamesException e) {
			System.out.println("ERROR: There cannot be two directories or two files with the same name.");
		}
	}

	/**
	 * Prints the tree starting from the root of the file system.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The tree is printed, and the user is prompted for another command.</dd>
	 * </dl>
	 */
	private static void listDirectoryFromRoot() {
		directoryTree.printDirectoryTreeFromRoot();
	}

	/**
	 * Prints the tree starting from the root of the file system.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>Moves the cursor to the root of the file system, and the user is prompted
	 * for another command.</dd>
	 * </dl>
	 */
	private static void moveToRoot() {
		directoryTree.resetCursor();
	}

	/**
	 * Moves the cursor up one level to the parent directory.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The cursor now points to the parent, and the user is prompted for another
	 * command.</dd>
	 * </dl>
	 */
	private static void moveToParent() {
		if (directoryTree.getCursor().equals(directoryTree.getRoot())) {
			System.out.println("ERROR: Already at the root directory");
		} else {
			directoryTree.goToParent();
		}
	}

	/**
	 * Moves the cursor through the specified path.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>If the path is valid, the cursor moves through the path, and the user is
	 * prompted for another command.</dd>
	 * </dl>
	 */
	private static void moveToDirectory(String path) {
		try {
			directoryTree.changeDirectory(path);
		} catch (UnresolvedPathException e) {
			System.out.println("ERROR: '" + path + "' cannot be found.");
		} catch (NotADirectoryException e) {
			System.out.println("ERROR: '" + path + "' is not a directory.");
		}
	}

	private static void moveDirectory(String sourcePath, String targetPath) {
		try {
			directoryTree.moveDirectory(sourcePath, targetPath);
		} catch (UnresolvedPathException e) {
			System.out.println("The source or target path is invalid.");
		} catch (NotADirectoryException e) {
			System.out.println("The target path does not resolve to a directory.");
		} catch (FullDirectoryException e) {
			System.out.println("The directory at the target path is already full.");
		} catch (ConflictingNamesException e) {
			System.out.println("There cannot be two directories or two files with the same name.");
		}
	}

	private static void find(String name) {
		String result = directoryTree.find(name);
		if (result.equals("")) {
			System.out.println("ERROR: No such file exists.");
		} else {
			directoryTree.printAllFound(name);
		}
	}

	/**
	 * Parses the input from the user.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The input is handled appropriately, or a message is shown if the input is
	 * invalid.</dd>
	 * </dl>
	 */
	private static void parseCommand(String command) {
		switch (command) {
		case "pwd":
			printWorkingDirectory();
			break;
		case "ls":
			listDirectory();
			break;
		case "ls -R":
			listDirectoryFromRoot();
			break;
		case "cd /":
			moveToRoot();
			break;
		case "cd ..":
			moveToParent();
			break;
		case "exit":
			System.out.println("Program terminating normally");
			System.exit(0);
			// Handle regex matching here
		default:
			if (command.matches("touch\\s+[^/\\s]+")) {
				makeFile(command.split(" ")[1].trim());
			} else if (command.matches("mkdir\\s+[^/\\s]+")) {
				makeDirectory(command.split(" ")[1].trim());
			} else if (command.matches("cd\\s+[^\\s]+")) {
				moveToDirectory(command.split(" ")[1].trim());
			} else if (command.matches("mv\\s+[^\\s]+\\s+[^\\s]+")) {
				moveDirectory(command.split(" ")[1].trim(), command.split(" ")[2].trim());
			} else if (command.matches("find\\s+[^/\\s]+")) {
				find(command.split(" ")[1].trim());
			} else {
				System.out.println("That is not a valid command.");
			}
		}
		getUserInput();
	}

	/**
	 * Runs a simulation that allows the user to interact with a file system.
	 * 
	 * <dl>
	 * <dt>Postconditions:</dt>
	 * <dd>The bash terminal has started up.</dd>
	 * </dl>
	 */
	public static void main(String[] args) {
		System.out.println("Starting Bash Terminal.");
		directoryTree = new DirectoryTree();
		in = new Scanner(System.in);
		getUserInput();
	}
}
