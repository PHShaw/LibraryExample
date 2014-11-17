package application;

/**
 * Customised exception for the file handling of the libary catalogue and member lists
 * @author phs
 *
 */
public class LibraryException extends Exception {
	
	private static final long serialVersionUID = -2308567964545837039L;
	private boolean items;	//returns true if error when saving/loading items
	private boolean users;	//returns true if error when saving/loading users
	
	public LibraryException() {
		super("An error occurred in the library");
	}

	public LibraryException(String arg0) {
		super(arg0);
	}

	public LibraryException(Throwable arg0) {
		super(arg0);
	}

	public LibraryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public LibraryException(String arg0, boolean users, boolean items) {
		super(arg0);
		this.users = users;
		this.items = items;
	}
	public LibraryException(String arg0, Throwable arg1, boolean users, boolean items) {
		super(arg0, arg1);
		this.users = users;
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public boolean isItems() {
		return items;
	}

	/**
	 * @return the users
	 */
	public boolean isUsers() {
		return users;
	}
	
}
