// import packages
package library.returnBook;
import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;

/*
Author: Saroj
Mediator: Anish
Reviewer: Bednidhi
*/

// class declaration
public class ReturnBookControl {

	// private variable declaration
	private ReturnBookUI ui;
	private enum ControlState { Initialised, Ready, Inspecting };
	private controlState state;
	
	private Library library;
	private Loan currentLoan;
	

	// return book control method
	public returnBookControl() {
		this.library= Library.getInstance();
		state = ControlState.Initialised;
	}
	
	
	public void setUi(ReturnBookUi ui) {
		if (!state.equals(ControlState.Initialised)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.ui = ui;
		ui.setState(ReturnBookUi.uiState.Ready);
		state = ControlState.Ready;		
	}

	
	public void bookScanned(int bookId) {
		if (!state.equals(ControlState.Ready)) {
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}
		
		Book currentBook = library.getBook(bookId);
		
		if (currentBook == null) {
			ui.display("Invalid Book Id");
			return;
		}
		if (!currentBook.isOnLoan()) {
			ui.display("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByBookId(bookId);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue())  {
			overDueFine = library.calculateOverDueFine(currentLoan);
		}
		
		ui.display("Inspecting");
		ui.display(currentBook.toString());
		ui.display(currentLoan.toString());
		
		if (currentLoan.isOverDue()) {
			ui.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		}
		
		ui.setState(ReturnBookUI.uiState.Inspecting);
		state = ControlState.Inspecting;		
	}

	public void scanningComplete() {
		if (!state.equals(ControlState.Ready)) {
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}
			
		ui.setState(ReturnBookUI.uiState.completed);		
	}


	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(ControlState.Inspecting)) {
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}
		
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		ui.setState(ReturnBookUI.uiState.Ready);
		state = ControlState.Ready;				
	}


}
