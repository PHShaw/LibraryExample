package application;

/**
 * Class extending the Item to represent a DVD
 * @author phs
 *
 */
public class DVD extends Item{
	protected final String director;
	protected final String studio;
	protected Genre genre;
	
	/**
	 * Construct a new DVD object
	 * @param title
	 * @param pubDate
	 * @param director
	 * @param studio
	 * @param genre
	 */
	public DVD(String title, int pubDate, String director, String studio, Genre genre) {
		super(title, pubDate);
		this.director = director;
		this.studio = studio;
		this.genre = genre;
	}

	/**
	 * Construct a new dvd from a set of strings loaded in from a file
	 * @param str
	 */
	public DVD(String[] str)
	{
		super(str);
		director = str[4];
		studio = str[5];
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
	 * Enum of Movie genres
	 * @author phs
	 *
	 */
	public enum Genre {SciFi, Fantasy, Documentary, Romance, Comedy, Action, Horror, Childrens, Unknown}
	
	
	
	/**
	 * Get the director of the film
	 * @return director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Get the studio of the film
	 * @return studio
	 */
	public String getStudio() {
		return studio;
	}

	/**
	 * Get the genre of the film
	 * @return genre
	 */
	public Genre getGenre() {
		return genre;
	}


	public String toString()
	{
		String str = "";
		str += "DVD ID: " + itemID + ", Title: " + title + ", Director: " + director + " Studio: " + studio + ", Published: " + publicationDate;
		str += " Genre: " + genre;
		if(available)
			str += ", Available";
		else
			str += ", On loan";
		
		return str;
	}
	
}