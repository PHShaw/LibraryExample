package application;

import java.util.ArrayList;

/**
 * Class that manages the items in the library catalogue
 * @author phs
 *
 */
public class Catalogue {
	private ArrayList<Item> items = new ArrayList<Item>();
	
	
	//Search by publication date (since date)

	
	
	/**
	 * @return the arraylist of items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	
	/**
	 * Searches all the items in the catalogue based on the title
	 * @param title can be full or substring from title
	 * @return all items that match the query
	 */
	public ArrayList<Item> searchAllByTitle(String title)
	{
		ArrayList<Item> hits = new ArrayList<Item>();
		for(Item i : items)
		{
			if(i.getTitle().toLowerCase().contains(title.toLowerCase()))
			{
				hits.add(i);
			}
		}
		
		return hits;
	}
	
	/**
	 * Search all the books for author
	 * @param author
	 * @return list of matching books
	 */
	public ArrayList<Item> searchBooksByAuthor(String author)
	{
		ArrayList<Item> hits = new ArrayList<Item>();
		for(Item i : items)
		{
			if(i instanceof Book)
			{
				Book b = (Book)i;
				
				if(b.getAuthor().toLowerCase().contains(author.toLowerCase()))
				{
					hits.add(b);
				}
			}
		}
		
		return hits;
	}
	
	/**
	 * Search all the dvds in the catalogue based on the director 
	 * @param director
	 * @return list of matching dvds
	 */
	public ArrayList<Item> searchDVDsByDirector(String director)
	{
		ArrayList<Item> hits = new ArrayList<Item>();
		for(Item i : items)
		{
			if(i instanceof DVD)
			{
				DVD d = (DVD)i;
				
				if(d.getDirector().toLowerCase().contains(director.toLowerCase()))
				{
					hits.add(d);
				}
			}
		}
		
		return hits;
	}
	
	/**
	 * Search all the cds in the catalogue based on the artist
	 * @param artist
	 * @return list of matching cds
	 */
	public ArrayList<Item> searchCDsByArtist(String artist)
	{
		ArrayList<Item> hits = new ArrayList<Item>();
		for(Item i : items)
		{
			if(i instanceof CD)
			{
				CD cd = (CD)i;
				
				if(cd.getArtist().toLowerCase().contains(artist.toLowerCase()))
				{
					hits.add(cd);
				}
			}
		}
		
		return hits;
	}
	
	
	/**
	 * Add a new book to the catalogue
	 * @param title
	 * @param pubDate
	 * @param author
	 * @param publisher
	 * @param genre
	 */
	public void addBook(String title, int pubDate, String author, String publisher, Book.Genre genre)
	{
		items.add(new Book(title, pubDate, author, publisher, genre));
	}
	
	/**
	 * Add a new cd to the catalogue
	 * @param title
	 * @param pubDate
	 * @param artist
	 * @param noTracks
	 * @param genre
	 */
	public void addCD(String title, int pubDate, String artist, int noTracks, CD.Genre genre)
	{
		items.add(new CD(title, pubDate, artist, noTracks, genre));
	}
	
	/**
	 * Add a new dvd to the catalogue
	 * @param title
	 * @param pubDate
	 * @param director
	 * @param studio
	 * @param genre
	 */
	public void addDVD(String title, int pubDate, String director, String studio, DVD.Genre genre)
	{
		items.add(new DVD(title, pubDate, director, studio, genre));
	}
	
	/**
	 * Add a new book using a string read from a file
	 * @param item array of strings representing a book object
	 */
	public void addBook(String[] item)
	{
		items.add(new Book(item));
	}
	/**
	 * Add a new cd using a string read from a file
	 * @param item array of strings representing a cd object
	 */
	public void addCD(String[] item)
	{
		items.add(new CD(item));
	}
	/**
	 * Add a new dvd using a string read from a file
	 * @param item array of strings representing a dvd object
	 */
	public void addDVD(String[] item)
	{
		items.add(new DVD(item));
	}

	/**
	 * Return an item from the catalogue based on its ID
	 * @param itemID
	 * @return	Item matching ID or null if no item is found
	 */
	public Item getItem(int itemID) {
		for(Item i : items)
		{
			if(i.itemID == itemID)
				return i;
		}
		return null;
	}


	/**
	 * Remove the specified item from the catalogue
	 * @param it Item to be removed
	 */
	public void remove(Item it) {
		items.remove(it);
	}
}
