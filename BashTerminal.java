package fileDirectory;

/**
 * The <code>BashTerminal</code> class gives the user an interface to interact
 * with the file tree simulation.
 * 
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class BashTerminal {
	public static void main(String[] args) {
		String workingDirectory = "root/test/test1/javascriptStuff/two";
		workingDirectory = workingDirectory.replaceAll("/[A-Za-z0-9]*$", "");
		System.out.println(workingDirectory);
	}
}
