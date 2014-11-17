package application;

import java.util.Scanner;

/**
 * Abstract representation of an object with the basic functionality included
 * @author phs
 *
 */
public abstract class Item {

	private static int counter = 0;
	protected final int itemID;
	protected final String title;
	protected boolean available;
	protected int publicationDate;		//Year of publication
	
	/**
	 * Constructor of common item elements
	 * @param title
	 * @param pubDate
	 */
	public Item(String title, int pubDate)
	{
		itemID = ++counter;
		this.title = title;
		this.publicationDate = pubDate;
		available = true;
	}
	
	/**
	 * This constructor is used for reloading an item from a file.
	 * @param entry a csv record for the item.
	 * 		<p> This should be formatted as follows:
	 * 			itemID,title,genre,...  
	 */
	public Item(String[] elements)
	{
		counter++;
//		String[] elements = entry.split(",");
		
		Scanner scan = new Scanner(elements[0]);
		if(scan.hasNextInt())
		{
			itemID = scan.nextInt();
			if(itemID>counter)
				counter = itemID;
		}
		else
		{
			System.err.println("Error reading Item ID from file, assigning next default ID");
			itemID = counter;
		}
		scan.close();
		title = elements[2];
		
		try {
			publicationDate = Integer.parseInt(elements[3]);
		} catch (NumberFormatException e) {
			System.err.println("Failed to load item publication date correctly, entering a default date");
			publicationDate = 2013;
		}
		
		available = true;
	}
	
	/**
	 * Get availability of the item
	 * @return true if item is available
	 */
	public boolean isAvailable()
	{
		return available;
	}
	/**
	 * Sets the availability of the item to true
	 */
	public void returnItem()
	{
		available = true;
	}
	/**
	 * Sets the availability of the item to false
	 */
	public void loanItem()
	{
		available = false;
	}

	/**
	 * Get the title of the item
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get the publication date of the item
	 * @return year of publication
	 */
	public int getPublicationDate() {
		return publicationDate;
	}
	
	
	public String toString()
	{
		String str = "";
		str += "ID: " + itemID + ", Title: " + title + ", published: " + publicationDate;
		if(available)
			str += ", Available";
		else
			str += ", On loan";
		
		return str;
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Item)
		{
			Item it = (Item)obj;
			return title.equals(it.title);
		}
		else
			return false;
	}
	
}
