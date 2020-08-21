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
	private Date date;  // changed DaTe to date
	private LoanState state;  // changed lOaN_sTaTe StAtE to LoanState state

	
	public Loan(int loanId, Book book, Member member, Date date) { // DuE_dAtE to date
		this.loadId = loanId;
		this.Book = book;
		this.Member = member;
		this.Date = date;
		this.state = LoanState.current; 
	}

	
	public void checkOverDue() { // changed method name from cHeCk_OvEr_DuE to checkOverDue
		if (state == LoanState.current &&
			Calendar.getInstance().getDate().after(DaTe)) 
			this.state = LoanState.overdue;			
		
	}

	
	public boolean Is_OvEr_DuE() { 
		return StAtE == lOaN_sTaTe.OVER_DUE;
	}

	
	public Integer GeT_Id() {
		return LoAn_Id;
	}


	public Date GeT_DuE_DaTe() {
		return DaTe;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(LoAn_Id).append("\n")
		  .append("  Borrower ").append(MeMbEr.GeT_ID()).append(" : ")
		  .append(MeMbEr.GeT_LaSt_NaMe()).append(", ").append(MeMbEr.GeT_FiRsT_NaMe()).append("\n")
		  .append("  Book ").append(BoOk.gEtId()).append(" : " )
		  .append(BoOk.gEtTiTlE()).append("\n")
		  .append("  DueDate: ").append(sdf.format(DaTe)).append("\n")
		  .append("  State: ").append(StAtE);		
		return sb.toString();
	}


	public Member GeT_MeMbEr() {
		return MeMbEr;
	}


	public Book GeT_BoOk() {
		return BoOk;
	}


	public void DiScHaRgE() {
		StAtE = lOaN_sTaTe.DISCHARGED;		
	}

}
