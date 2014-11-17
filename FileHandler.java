package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Class for managing the reading and writing to file for the Library application
 * @author phs
 *
 */
public class FileHandler {

	private static String userFilename = "members.txt";
	private static String catalogueFilename = "catalogue.txt";
	private static String token = "|";
	
	
	/**
	 * Save the lists of members and items to a file.  Loans are stored with the members in the user file
	 * @param members
	 * @param items
	 * @return	success of the save operation
	 * @throws LibraryException
	 */
	public static boolean save(ArrayList<Member> members, ArrayList<Item> items) throws LibraryException
	{
		String exception = "";
		boolean bUsers=false,bItems=false;
		
		try {
			saveMembers(members);
		} catch (IOException e1) {
			exception = "An IO error occured when trying to save the members";
			bUsers = true;
		}
		
		try {
			saveItems(items);
		} catch (IOException e) {
			exception += "/n An IO error occured when trying to save the items";
			bItems = true;
		}
		
		
		if(bUsers || bItems)
		{
			throw new LibraryException(exception, bUsers, bItems);
		}
		
		return true;
	}



	private static void saveItems(ArrayList<Item> items) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(catalogueFilename));
		for(Item it : items)
		{
			writer.write(it.itemID + "");
			writer.write(token);
			
			if(it instanceof Book)
			{
				Book b = (Book)it;
				writer.write("BOOK");
				writer.write(token);
				writer.write(b.title);
				writer.write(token);
				writer.write(b.publicationDate + "");
				writer.write(token);
				
				writer.write(b.author);
				writer.write(token);
				writer.write(b.publisher);
				writer.write(token);
				writer.write(b.genre.toString());
			}
			else if(it instanceof CD)
			{
				CD c = (CD)it;
				writer.write("CD");
				writer.write(token);
				writer.write(c.title);
				writer.write(token);
				writer.write(c.publicationDate + "");
				writer.write(token);
				
				writer.write(c.artist);
				writer.write(token);
				writer.write(c.noTracks + "");
				writer.write(token);
				writer.write(c.genre.toString());
			}
			else if(it instanceof DVD)
			{
				DVD d = (DVD)it;
				writer.write("DVD");
				writer.write(token);
				writer.write(d.title);
				writer.write(token);
				writer.write(d.publicationDate + "");
				writer.write(token);
				
				writer.write(d.director);
				writer.write(token);
				writer.write(d.studio);
				writer.write(token);
				writer.write(d.genre.toString());
			}
			
			writer.newLine();
		}
		
		writer.close();
	}


	private static void saveMembers(ArrayList<Member> members) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(userFilename));
		for(Member mem : members)
		{
			writer.write(mem.memberID + "");
			writer.write(token);
			writer.write(mem.name);
			writer.write(token);
			writer.write(mem.dob.toString());
			
			for(Loan l : mem.loans)
			{
				writer.write(token);
				writer.write(l.getItem().itemID + "");
				writer.write(token);
				writer.write(l.getDueDate().toString());
			}
			
			writer.newLine();
		}
		
		writer.close();
	}
	
	
	
	
	/**
	 * Load the array list of items from a file for repopulating the catalogue
	 * @return arraylist with each line separated into the tokens to represent each type of object
	 */
	public static ArrayList<String[]> loadItems()
	{
		ArrayList<String[]> items = new ArrayList<String[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(catalogueFilename));
			while(reader.ready())
			{
				String line = reader.readLine();
				StringTokenizer tokens = new StringTokenizer(line,token);
				
				String[] temp = new String[tokens.countTokens()];
				try{
					for(int i=0; i<temp.length; i++)
					{
						temp[i] = tokens.nextToken();
					}
				}catch(NoSuchElementException e) {
					System.out.println("Oops, tried to read in too many item tokens, moving onto next line");
				}
				
				items.add(temp);
				
			}
				
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("No file of items was found, continuing without loading catalogue");
		} catch (IOException e) {
			System.err.println("An error occurred whilst trying to load the catalogue");
		} 


		return items;
	}
	
	/**
	 * Load the array list of members from the file
	 * @return Array list containing each member, with object tokens separated out
	 */
	public static ArrayList<String[]> loadMembers()
	{
		ArrayList<String[]> members = new ArrayList<String[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(userFilename));
			while(reader.ready())
			{
				String line = reader.readLine();
				StringTokenizer tokens = new StringTokenizer(line,token);
				
				String[] temp = new String[tokens.countTokens()];
				try{
					for(int i=0; i<temp.length; i++)
					{
						temp[i] = tokens.nextToken();
					}
				}catch(NoSuchElementException e) {
					System.out.println("Oops, tried to read in too many member tokens, moving onto next line");
				}
				
				members.add(temp);
			}
				
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("No file of members was found, continuing without loading members");
		} catch (IOException e) {
			System.err.println("An error occurred whilst trying to load the list of existing members");
		} 
		
		return members;
	}
}
