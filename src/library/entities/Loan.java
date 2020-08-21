/*Author: Roshan
Mediator: Bednidhi
Reviewer: Saroj*/

// import package
package library.entities;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable {
	
	public static enum LoanState { current, overdue, discharged }; // change class name from lOaN_sTaTe to LoanState and OVER_DUE to overdue
	
	// changed varriable name and class name 
	private int loanId; // changed LoAn_Id to loanId
	private Book book; // changed BoOk to book
	private Member member;  // changed MeMbEr to member
	private Date dueDate;  // changed DaTe to dueDate
	private LoanState state;  // changed lOaN_sTaTe StAtE to LoanState state

	
	public Loan(int loanId, Book book, Member member, Date dueDate) { // DuE_dAtE to dueDate
		this.loadId = loanId;
		this.Book = book;
		this.Member = member;
		this.Date = dueDate;
		this.state = LoanState.current; 
	}

	
	public void checkOverDue() { // changed method name from cHeCk_OvEr_DuE to checkOverDue
		if (state == LoanState.current &&
			Calendar.getInstance().getDate().after(Date)) 
			this.state = LoanState.overdue;			
		
	}

	
	public boolean isOverDue() { // changed method name from Is_OvEr_DuE to isOverDue
		return state == LoanState.overdue;
	}

	
	public int getId() { // changed method name from GeT_Id to getId
		return loanId;
	}


	public Date getDueDate() { //  changed method name fromGeT_DuE_DaTe to getDueDate
		return date;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(loanId).append("\n")
		  .append("  Borrower ").append(member.getId()).append(" : ")
		  .append(member.getLastName()).append(", ").append(member.getFirstName()).append("\n")
		  .append("  Book ").append(Book.getId()).append(" : " )
		  .append(Book.getTitle()).append("\n")
		  .append("  DueDate: ").append(sdf.format(Date)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public Member getMember() { //changed method name from GeT_MeMbEr to getMember
		return member;
	}


	public Book getBook() { //changed method name from GeT_BoOk to getBook
		return book;
	}


	public void discharge() { //changed method name from DiScHaRgE to discharge
		state = loanState.discharge;		
	}

}
