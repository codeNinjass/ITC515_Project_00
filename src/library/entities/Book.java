//import packages
package library.entities;
import java.io.Serializable;

//Author: Anish Baniya
//Mediator: Saroj Maharjan
//Reviewer: Roshan Karki


@SuppressWarnings("serial")
public class Book implements Serializable {
	
	private String title; //changed to title
	private String author; //changed to author
	private String callNumber; //changed to callNumber
	private int bookId; //changed to bookId
	
	private enum StateOfBook { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };//changed to StatedOfBook
	private StateOfBook state; //changed to state
	
	
	public Book(String author, String title, String callNo, int bookId) { //changed to bookId and callNumber
		this.author = author; //changed to author
		this.title = title; //changed to title
		this.callNumber = callNumber; //changed to callNumber
		this.bookId = id; //changed to bookId
		this.state = StateOfBook.AVAILABLE; //changed to state and StateOfBook
	}
	
	public String toString() {
		StringBuilder bookStringBuilder = new StringBuilder(); //changed to from sb->bookStringBuilder
		sb.append("Book: ").append(this.bookId).append("\n") //changed to this.bookId
		  .append("  Title:  ").append(this.title).append("\n") //changed to this.title
		  .append("  Author: ").append(this.author).append("\n") //changed to this.author
		  .append("  CallNo: ").append(this.callNumber).append("\n") //changed to this.callNumber
		  .append("  State:  ").append(this.state); //changed to this.state
		
		return bookStringBuilder.toString();//changed to bookStringBuilder
	}

	public Integer getBookId() {//changed to getBookId
		return this.bookId;
	}

	public String getTitle() {//changed to getTitle
		return this.title;
	}


	
	public boolean isAvailable() {//changed to isAvailable
		return this.state == StateOfBook.AVAILABLE;
	}

	
	public boolean iS_On_LoAn() {
		return StAtE == sTaTe.ON_LOAN;
	}

	
	public boolean iS_DaMaGeD() {
		return StAtE == sTaTe.DAMAGED;
	}

	
	public void BoRrOw() {
		if (StAtE.equals(sTaTe.AVAILABLE)) 
			StAtE = sTaTe.ON_LOAN;
		
		else 
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", StAtE));
		
		
	}


	public void ReTuRn(boolean DaMaGeD) {
		if (StAtE.equals(sTaTe.ON_LOAN)) 
			if (DaMaGeD) 
				StAtE = sTaTe.DAMAGED;
			
			else 
				StAtE = sTaTe.AVAILABLE;
			
		
		else 
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", StAtE));
				
	}

	
	public void RePaIr() {
		if (StAtE.equals(sTaTe.DAMAGED)) 
			StAtE = sTaTe.AVAILABLE;
		
		else 
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", StAtE));
		
	}


}
