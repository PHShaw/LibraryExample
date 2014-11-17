package application;

import java.util.Calendar;
import java.util.Date;

/**
 * Class representing loans.  These are stored in a list for each member, so only point to the item and due date
 * @author phs
 *
 */
public class Loan {

	private final Item item;
	private final Date dueDate;
	
	public Loan(Item i, Date due)
	{
		item = i;
		item.loanItem();
		dueDate = due;
	}
	public Loan(Item i)
	{
		item = i;
		item.loanItem();
		Date now=new Date();
		Calendar dateCalc=Calendar.getInstance();
		dateCalc.setTime(now);
		dateCalc.add(Calendar.DAY_OF_YEAR, 28);
		this.dueDate=dateCalc.getTime();
	}
	
	
	public Item getItem() {
		return item;
	}
	public Date getDueDate() {
		return dueDate;
	}
	
	
	public String toString()
	{
		return item + " due " + dueDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		item.returnItem();
		super.finalize();
		
	}
	
	
	
}
