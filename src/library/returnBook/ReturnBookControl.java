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

	private ReturnBookUI UI;
	private enum controlState { initialised, ready, inspecting };
	private controlState state;
	
	private Library library;
	private Loan currentLoan;
	

	public ReturnBookControl() {
		this.library= Library.getInstance();
		state = controlState.initialised;
	}
	
	
	public void setUI(ReturnBookUI UI) {
		if (!state.equals(controlState.initialised)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.UI = UI;
		UI.setState(ReturnBookUI.uiState.ready);
		state = controlStateReady;		
	}

	
	public void bookScanned(int bookId) {
		if (!state.equals(controlState.ready)) 
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		
		Book currentBook = library.getBook(bookId);
		
		if (currentBook == null) {
			UI.display("Invalid Book Id");
			return;
		}
		if (!currentBook.isOnLoan()) {
			UI.display("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByBookId(bookId);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue()) 
			overDueFine = library.calculateOverDueFine(currentLoan);
		
		UI.display("Inspecting");
		UI.display(currentBook.toString());
		UI.display(currentLoan.toString());
		
		if (currentLoan.isOverDue()) 
			UI.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		
		UI.setState(ReturnBookUI.uiState.inspecting);
		state = controlState.inspecting;		
	}


	public void scanningComplete() {
		if (!state.equals(controlState.ready)) 
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
			
		UI.setState(ReturnBookUI.uiState.completed);		
	}


	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(controlState.inspecting)) 
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		UI.setState(ReturnBookUI.uiState.ready);
		state = controlState.ready;				
	}


}
