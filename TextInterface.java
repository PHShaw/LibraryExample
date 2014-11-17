package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Scanner;

/**
 * This class implements a console based interface for using a Library
 * While the model is separate to the interface, the view and control are currently
 * merged in this class.  To step towards separating these out, a separate class could be
 * added that contains the two input methods (getIntInput(), getStringInput()), along
 * with a third method for standard output, and a fourth for error output.  These
 * methods would then be called instead of System.out.println() or System.err.println()
 *  
 * @author Patricia Shaw
 *
 */
public class TextInterface {

	private static Library library;
//	private static Scanner scan;
	private static BufferedReader reader;
	

	public static void textInterface()
	{
		initialise();
		while(true)
		{
			printMenu();
			int input = getIntInput();
			processMenuInput(input);
			
		}
	}
	
	
	private static void printMenu()
	{
		System.out.println("Please select from the following options:");
		System.out.println("\t1. Add a new user");
		System.out.println("\t2. Add a new item");
		System.out.println("\t3. Search the catalogue");
		System.out.println("\t4. Loan an item to a member");
		System.out.println("\t5. Return an item from a member");
		System.out.println("\t6. List all items");
		System.out.println("\t7. List all users");
		System.out.println("\t8. Search for a member");
		System.out.println("\t9. Remove an item from the catalogue");
		System.out.println("\t10. Exit");
	}
	

	private static void processMenuInput(int input)
	{
		switch (input) {
		case 1:
			addUser();
			break;
			
		case 2:
			addItem();
			break;
			
		case 3:
			search();
			break;
		case 4:
			loan();
			break;
		case 5:
			returnItem();
			break;
		case 6:
			listItems();
			break;
		case 7:
			listUsers();
			break;
		case 8:
			searchMember();
			break;
		case 9:
			removeItem();
			break;
			
		case 10:
			close();
			break;

		default:
			break;
		}
	}
	
	

	private static void removeItem() {
		Item it = getItem();
		library.remove(it);
		
	}


	private static void searchMember() {
		Member m = getMember();
		System.out.println(m);
		
	}


	private static void listItems() {
		ArrayList<Item> catalogue = library.getCatalogue().getItems();
		for(Item i : catalogue)
		{
			System.out.println(i);
		}
		
	}


	private static void listUsers() {
		ArrayList<Member> members = library.getMembers();
		for(Member m : members)
		{
			System.out.println(m);
		}
	}


	private static void loan() {

		
		Member m = getMember();
		if(m==null)
		{
			System.out.println("Unable to lend to member");
			return;
		}
		else
			System.out.println("Lending to: " + m);
		
		Item i = getItem();
		if(i==null)
		{
			System.out.println("Unable to lend item");
		}
		else
			System.out.println("Item: " + i);
		
		boolean success = library.loan(m, i);
		if(success)
			System.out.println("The item has sucessfully been loaned out");
		else
			System.out.println("This item could not be loaned to this member");
	}
	
	
	private static void returnItem()
	{
		Item i = getItem();
		if(i==null)
		{
			System.out.println("Unable to locate the item in the catalogue");
		}
		else if(i.isAvailable())
		{
			System.out.println("This item is currently not marked as being on loan");
		}
		else
		{
			boolean success = library.returnItem(i);
			if(success)
				System.out.println("The item has successfully been returned to the library");
			else
				System.out.println("Unable to locate the member that had this item on loan");
		}
	}
	
	private static Member getMember()
	{
		System.out.println("Please enter the name of the library member");
		String name = getStringInput();
		
		ArrayList<Member> results = library.searchMembers(name);
		if(results.size()==0)
		{
			System.out.println("No matching member found");
			return null;
		}
		else if(results.size()==1)
			return results.get(0);
		else
		{
			System.out.println("Please select the correct entry from the list below");
			for(int i=0; i<results.size(); i++)
			{
				System.out.println((i+1) + ". " + results.get(i));
			}
			int input = getIntInput();
			if(input > results.size() || input<=0)
			{
				System.out.println("Selection is out of range, returning to menu");
				return null;
			}
			else
			{
				return results.get(input-1);
			}
		}
	}

	
	
	private static Item getItem() {
		System.out.println("Enter the title of the item");
		String title = getStringInput();
		ArrayList<Item> results = library.searchAllByTitle(title);
		if(results.size()==0)
		{
			System.out.println("No item was found");
			return null;
		}
		else if(results.size()==1)
			return results.get(0);
		else
		{
			System.out.println("Please select the correct entry from the list below");
			for(int i=0; i<results.size(); i++)
			{
				System.out.println((i+1) + ". " + results.get(i));
			}
			int input = getIntInput();
			if(input > results.size() || input<=0)
			{
				System.out.println("Selection is out of range, returning to menu");
				return null;
			}
			else
			{
				return results.get(input-1);
			}
		}
	}

	
	
	
	private static void search() {
		
		while(true)
		{
			System.out.println("Please select from the following options:");
			System.out.println("\t1. Search all by title");
			System.out.println("\t2. Search books by author");
			System.out.println("\t3. Search DVDs by director");
			System.out.println("\t4. Search CDs by artist");
			System.out.println("\t5. Return to menu");
			
			int choice = getIntInput();
		
			switch (choice) {
			case 1:
				searchAll();
				break;
			case 2:
				searchBooks();
				break;
			case 3:
				searchDVDs();
				break;
			case 4:
				searchCDs();
				break;
			case 5:
				return;
			default:
				System.out.println("The option you selected was not recognised, please try again");
				break;
			}
		}
			
		
	}

	private static void searchCDs() {
		System.out.println("Enter the name of the artist to search for");
		String artist = getStringInput();
		ArrayList<Item> results = library.searchCDsByArtist(artist);
		if(results.size()==0)
			System.out.println("No results were returned");
		else
		{
			printList(results);
		}
	}


	private static void searchDVDs() {
		System.out.println("Enter the name of the director to search for");
		String director = getStringInput();
		ArrayList<Item> results = library.searchDVDsByDirector(director);
		if(results.size()==0)
			System.out.println("No results were returned");
		else
		{
			printList(results);
		}
	}


	private static void searchBooks() {
		System.out.println("Enter the name of the author to search for");
		String author = getStringInput();
		ArrayList<Item> results = library.searchBooksByAuthor(author);
		if(results.size()==0)
			System.out.println("No results were returned");
		else
		{
			printList(results);
		}
	}


	private static void searchAll() {
		System.out.println("Enter the title to search for");
		String title = getStringInput();
		ArrayList<Item> results = library.searchAllByTitle(title);
		if(results.size()==0)
			System.out.println("No results were returned");
		else
		{
			printList(results);
		}		
	}

	
	private static void printList(ArrayList<Item> results)
	{
		int counter = 1;
		System.out.println("There are " + results.size() + " results:");
		for(Item i : results)
		{
			System.out.println(counter + ". " + i);
			counter ++;
		}
	}
	

	private static void addItem()
	{
		System.out.println("Please select the type of item to be entered:");
		System.out.println("\t1. Book");
		System.out.println("\t2. CD");
		System.out.println("\t3. DVD");
		int input = getIntInput();
		switch (input) {
		case 1:
			addBook();
			break;
			
		case 2:
			addCD();
			break;
			
		case 3:
			addDVD();
			break;

		default:
			System.err.println("Unrecognised selection");
			break;
		}
	}
		
	private static void addDVD() {
		System.out.println("Please enter the title of the DVD");
		String title = getStringInput();
		
		System.out.println("Please enter the release date (yyyy)");
		int pubDate = getIntInput();
		
		System.out.println("Please enter the name of the director");
		String director = getStringInput();
		
		System.out.println("Please enter the name of the film studio");
		String studio = getStringInput();
		
		DVD.Genre[] values = DVD.Genre.values();
		System.out.println("Please select one of the following genres for the film");
		for(int i=0; i<values.length; i++)
		{
			System.out.println((i+1) + ". " + values[i]);
		}
		int iGenre =0;
		do {
			System.out.println("Enter a number between 1 and " + values.length);
			iGenre= getIntInput();
		}while(iGenre>=values.length);
		
		DVD.Genre genre = values[iGenre-1];
		
		library.addDVD(title, pubDate, director, studio, genre);
		System.out.println("DVD has successfully been added");
		
	}
	private static void addCD() {
		System.out.println("Please enter the title of the CD");
		String title = getStringInput();
		
		System.out.println("Please enter the release date (yyyy)");
		int pubDate = getIntInput();
		
		System.out.println("Please enter the name of the artist");
		String artist = getStringInput();
		
		System.out.println("Please enter the number of tracks");
		int noTracks = getIntInput();
		
		CD.Genre[] values = CD.Genre.values();
		System.out.println("Please select one of the following genres for the CD");
		for(int i=0; i<values.length; i++)
		{
			System.out.println((i+1) + ". " + values[i]);
		}
		int iGenre =0;
		do {
			System.out.println("Enter a number between 1 and " + values.length);
			iGenre= getIntInput();
		}while(iGenre>=values.length);
		
		CD.Genre genre = values[iGenre-1];
		
		library.addCD(title, pubDate, artist, noTracks, genre);	
		System.out.println("CD has successfully been added");
	}
	private static void addBook() {
		System.out.println("Please enter the title of the Book");
		String title = getStringInput();
		
		System.out.println("Please enter the publication date (yyyy)");
		int pubDate = getIntInput();
		
		System.out.println("Please enter the name of the author");
		String author = getStringInput();
		
		System.out.println("Please enter the name of the publisher");
		String publisher = getStringInput();
		
		Book.Genre[] values = Book.Genre.values();
		System.out.println("Please select one of the following genres for the book");
		for(int i=0; i<values.length; i++)
		{
			System.out.println((i+1) + ". " + values[i]);
		}
		int iGenre =0;
		do {
			System.out.println("Enter a number between 1 and " + values.length);
			iGenre= getIntInput();
		}while(iGenre>=values.length);
		
		Book.Genre genre = values[iGenre-1];
		
		library.addBook(title, pubDate, author, publisher, genre);
		System.out.println("Book has successfully been added");
	}
	private static void addUser()
	{
		System.out.println("Please enter the name of the new user");
		String name = getStringInput();
		
		System.out.println("Please enter date of birth of the new user (dd-mm-yyyy)");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String sdob = getStringInput();
		Date dob;
		try {
			dob = dateFormat.parse(sdob);
		} catch (ParseException e) {
			System.err.println("Failed to read date correctly, entering a default date");
			dob = new Date();
		}
		
		library.addUser(name, dob);
		System.out.println("User has successfully been added");
	}

	private static int getIntInput()
	{
		boolean gotInput = false;
		int input = 0;
		while(!gotInput)
		{
			try {
				String line = reader.readLine();
				input = Integer.parseInt(line);
				gotInput = true;
			} catch (IOException e) {
				System.err.println("Error reading int input");
			} catch(NumberFormatException e) {
				gotInput = false;
				System.err.println("Please enter a number");
			}
			
//			if(scan.hasNextInt())
//			{
//				input = scan.nextInt();
//				gotInput = true;
//			}
//			else
//			{
//				System.err.println("Please enter a number");
//			}
		}
		return input;
	}
	
	private static String getStringInput()
	{
		try {
			return reader.readLine();
		} catch (IOException e) {
			System.err.println("Error reading input");
		}
		return "";
//		return scan.next();
	}
	
	
	private static void initialise()
	{
		library = new Library();
		library.load();
//		scan = new Scanner(System.in);
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	private static void close()
	{
//		scan.close();
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("Error closing reader");
		}
		try{
			library.save();
		} catch (LibraryException e) {
			if(e.isItems())
			{
				System.err.println("An error occurred whilst trying to save the item catalogue");
			}
			if(e.isUsers())
			{
				System.err.println("An error occurred whilst trying to save the member list");
			}
		}
		System.exit(0);
	}
	
	
}
