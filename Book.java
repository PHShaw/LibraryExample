package application;

/**
 * An extension of the item class for representing a book
 * @author phs
 *
 */
public class Book extends Item{
	protected final String author;
	protected final String publisher;
	protected Genre genre;
	
	/**
	 * An enum containing a set of categories used by books
	 * @author phs
	 *
	 */
	public enum Genre {SciFi, Fantasy, Fiction, Reference, Horror, Childrens, Unknown}
	
	/**
	 * Create a new book
	 * @param title		Title
	 * @param pubDate	year of publication
	 * @param author	author
	 * @param publisher	publishing house
	 * @param genre		genre of book
	 */
	public Book(String title, int pubDate, String author, String publisher, Book.Genre genre)
	{
		super(title, pubDate);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
	}
	
	/**
	 * Create a new book from an array of strings read in from a file
	 * 	Warning: no checking is performed on the number of elements
	 * @param str	line from file separated into an array of elements
	 */
	public Book(String[] str)
	{
		super(str);
		author = str[4];
		publisher = str[5];
		Genre[] genres = Genre.values();
		boolean found = false;
		for(Genre g : genres)
		{
			if(str[6].equals(g.toString()))
			{
				this.genre = g;
				found = true;
				break;
			}
		}
		if(!found)
			this.genre = Genre.Unknown;
	}

	/**
	 * Get the book author
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Get the book publisher
	 * @return publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Get the book genre
	 * @return genre
	 */
	public Genre getGenre() {
		return genre;
	}
	
	
	/**
	 * Return a representation of the object as a string
	 */
	@Override
	public String toString()
	{
		String str = "";
		str += "Book ID: " + itemID + ", Title: " + title + ", Author: " + author + ", Published: " + publicationDate;
		str += " Genre: " + genre;
		if(available)
			str += ", Available";
		else
			str += ", On loan";
		
		return str;
	}
	
}