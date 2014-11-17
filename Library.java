package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Library class containing the catalogue, members and the core functionality for the library model
 * @author phs
 *
 */
public class Library {
	private Catalogue catalogue;
	private ArrayList<Member> members;
	
	
	
	public Library() {
		catalogue = new Catalogue();
		members = new ArrayList<Member>();
	}

	public void addUser(String name, Date dob) {
		members.add(new Member(name, dob));
	}

	public void save() throws LibraryException{
		FileHandler.save(members, catalogue.getItems());
	}

	/**
	 * Load the users and catalogue from a file
	 */
	public void load() {
		//First need to load items into the catalogue
		ArrayList<String[]> itemStrings = FileHandler.loadItems();
		
		for(String[] st : itemStrings)
		{
			String type = st[1];
			if(type.equals("BOOK"))
			{
				catalogue.addBook(st);
			}
			else if(type.equals("CD"))
			{
				catalogue.addCD(st);
			}
			else if(type.equals("DVD"))
			{
				catalogue.addDVD(st);
			}
			else
			{
				//unknown item read in;
			}
		}
		
		
		ArrayList<String[]> memberStrings = FileHandler.loadMembers();
		for(String[] st : memberStrings)
		{
			members.add(new Member(st));
				
			int memberID = 0;
			Scanner scan = new Scanner(st[0]);
			if(scan.hasNextInt())
			{
				memberID = scan.nextInt();
			}
			else
			{
				System.err.println("Error reading member ID from file, assigning next default ID");
			}
			scan.close();
			
			//now deal with their loans
			//member elements 0-2, any further elements are related to loans, in pairs
			int loanCount = (st.length-3)/2;
			for(int i=0; i<loanCount; i++)
			{
				int itemID =0;
				scan = new Scanner(st[i*2+3]);
				if(scan.hasNextInt())
				{
					itemID = scan.nextInt();
				}
				else
				{
					System.err.println("Error reading loaned Item ID from file, assigning next default ID");
				}
				scan.close();
				
				//Process the date string to get due date
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
				try {
					calendar.setTime(dateFormat.parse(st[i*2+3+1]));
				} catch (ParseException e) {
					//Error processing date, using default date
				}
				Date due=calendar.getTime();
				
				
				Item item = catalogue.getItem(itemID);
				Member mem = getMember(memberID);
				if(item == null || mem == null)
					System.err.println("Error regenerating loan");
				else
				{
					loan(mem, item, due);
				}
				
			}
		}
		
		
	}

	
	private Member getMember(int memberID) {
		for(Member m : members)
		{
			if(m.memberID == memberID)
			{
				return m;
			}
		}
		return null;
	}

	public void addBook(String title, int pubDate, String author, String publisher, Book.Genre genre)
	{
		catalogue.addBook(title, pubDate, author, publisher, genre);
	}
	
	public void addCD(String title, int pubDate, String artist, int noTracks, CD.Genre genre)
	{
		catalogue.addCD(title, pubDate, artist, noTracks, genre);
	}
	
	public void addDVD(String title, int pubDate, String director, String studio, DVD.Genre genre)
	{
		catalogue.addDVD(title, pubDate, director, studio, genre);
	}
	
	
	/**
	 * All search functionality is not case sensitive and is based on substrings
	 * @param title Search term
	 * @return array list containing matching items
	 */
	public ArrayList<Item> searchAllByTitle(String title)
	{
		return catalogue.searchAllByTitle(title);
	}
	
	public ArrayList<Item> searchBooksByAuthor(String author)
	{
		return catalogue.searchBooksByAuthor(author);
	}
	
	public ArrayList<Item> searchDVDsByDirector(String director)
	{
		return catalogue.searchDVDsByDirector(director);
	}
	
	public ArrayList<Item> searchCDsByArtist(String artist)
	{
		return catalogue.searchCDsByArtist(artist);
	}
	
	public ArrayList<Member> searchMembers(String name)
	{
		ArrayList<Member> hits = new ArrayList<Member>();
		for(Member m : members)
		{
			if(m.getName().toLowerCase().contains(name.toLowerCase()))
				hits.add(m);
		}
		return hits;
	}
	
	/**
	 * Issue an item out to a member.  This checks the lender has not reached their maximum number of loans
	 * @param m Member to lend item to 
	 * @param i Item to lend out
	 * @return True if the item has been successfully lent out
	 */
	public boolean loan(Member m, Item i)
	{
		boolean success = false;
		if(i.isAvailable())
		{
			if(m.getLoans().size() >= m.getMaxLoans())
				success = false;
			else
			{
				m.borrow(new Loan(i));
				success = true;
			}
		}
		return success;
	}
	private void loan(Member m, Item i, Date due)
	{
		m.borrow(new Loan(i,due));
	}
	
	/**
	 * Allows a user to return an item
	 * @param i	The item to be returned
	 * @return true if the item has been returned, false if unable to find member that borrowed item
	 */
	public boolean returnItem(Item i)
	{
		boolean located = false;
		Member borrower = null;
		Loan loan = null;
		for(Member m : members)
		{
			ArrayList<Loan> loans = m.getLoans();
			for(Loan l : loans)
			{
				if(l.getItem().equals(i))
				{
					located = true;
					borrower = m;
					loan = l;
					
				}
			}
		}
		if(located)
		{
			loan.getItem().returnItem();	//set the item as available again here 
			borrower.returnItem(loan);	//removing loan from members list here to avoid concurrent modification of list
			
		}
		
		return located;
			
	}

	/**
	 * @return the catalogue
	 */
	public Catalogue getCatalogue() {
		return catalogue;
	}

	/**
	 * @return the members
	 */
	public ArrayList<Member> getMembers() {
		return members;
	}

	/**
	 * Allows an item to be removed from the catalogue.
	 * If the item is currently listed as on loan then the item is returned from the member first
	 * @param it Item to be returned
	 */
	public void remove(Item it) {
		//Check to see if it is currently on loan, if so, return it!
		if(!it.isAvailable())
		{
			returnItem(it);
		}
		catalogue.remove(it);
	}
	
}
