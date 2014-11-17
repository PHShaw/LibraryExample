package application;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Class describing a user of the library
 * @author phs
 *
 */
public class Member extends Person
{
	private static int counter=0;
	protected int memberID;
	protected int maxLoans;
	
	protected ArrayList<Loan> loans;
	

	public Member(String name, Date dob)
	{
		super(name, dob);
		memberID = ++counter;
		maxLoans = 6;
		loans = new ArrayList<Loan>(maxLoans);
	}
	
	public Member(String[] str)
	{
		super(str);
		counter++;
		Scanner scan = new Scanner(str[0]);
		if(scan.hasNextInt())
		{
			memberID = scan.nextInt();
			if(memberID>counter)
				counter = memberID;
		}
		else
		{
			System.err.println("Error reading Member ID from file, assigning next default ID");
			memberID = counter;
		}
		scan.close();
		maxLoans = 6;
		loans = new ArrayList<Loan>(maxLoans);
	}
	
	
	/**
	 * Staff member have additional access rights to library members, currently not used
	 * @author phs
	 *
	 */
	public class Staff extends Member
	{
		public Staff(String name, Date dob)
		{
			super(name, dob);
			maxLoans = 20;
		}
	}
	

	/**
	 * @return the maxLoans
	 */
	public int getMaxLoans() {
		return maxLoans;
	}



	/**
	 * @return the loans
	 */
	public ArrayList<Loan> getLoans() {
		return loans;
	}
	
	public void borrow(Loan l)
	{
		loans.add(l);
	}
	
	public void returnItem(Item i)
	{
		for(Loan l : loans)
		{
			if(l.getItem().equals(i)){
				loans.remove(l);
				return;
			}
		}
	}
	
	public void returnItem(Loan l)
	{
		loans.remove(l);
	}

	
	public String toString()
	{
		String s = super.toString() + ", " + memberID;
		int counter = 1;
		for(Loan l : loans)
		{
			s += "\n\t Loan " + counter + ": " + l;
			counter ++;
		}
		return s;
	}
}