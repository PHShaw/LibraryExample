package application;

import java.util.Scanner;

/**
 * Extension of Item to represent a CD
 * @author phs
 *
 */
public class CD extends Item{
	protected final String artist;
	protected final int noTracks;
	protected Genre genre;
	
	/**
	 * Enum of music genres
	 * @author phs
	 *
	 */
	public enum Genre {Classical, Pop, Rock, Folk, Alternative, Disco, Rap, Unknown}

	/**
	 * Construct a new CD instance
	 * @param title
	 * @param pubDate
	 * @param artist
	 * @param noTracks
	 * @param genre
	 */
	public CD(String title, int pubDate, String artist, int noTracks, Genre genre) {
		super(title, pubDate);
		this.artist = artist;
		this.noTracks = noTracks;
		this.genre = genre;
	}
	
	/**
	 * Construct a new CD from a string read from the file
	 * @param str array of strings representing a CD object
	 */
	public CD(String[] str)
	{
		super(str);
		artist = str[4];
		Scanner scan = new Scanner(str[5]);
		if(scan.hasNextInt())
		{
			noTracks = scan.nextInt();
		}
		else
		{
			System.err.println("Error reading noTracks from file, assigning next default value");
			noTracks = 0;
		}
		scan.close();
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
	 * Return the CD artist	
	 * @return artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Return the number of tracks
	 * @return no tracks
	 */
	public int getNoTracks() {
		return noTracks;
	}

	/**
	 * Return the genre of the cd
	 * @return genre
	 */
	public Genre getGenre() {
		return genre;
	}


	public String toString()
	{
		String str = "";
		str += "CD ID: " + itemID + ", Title: " + title + ", Artist: " + artist + " Tracks: " + noTracks + ", Published: " + publicationDate;
		str += " Genre: " + genre;
		if(available)
			str += ", Available";
		else
			str += ", On loan";
		
		return str;
	}
	
	
	
	
	
}