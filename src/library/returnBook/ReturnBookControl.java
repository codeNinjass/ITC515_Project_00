// import packages
package library.returnBook;
import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;

public class ReturnBookControl {

	private ReturnBookUI Ui;
	private enum controlState { initialised, ready, inspecting };
	private controlState state;
	
	private Library library;
	private Loan currentLoan;
	

	public ReturnBookControl() {
		this.library= Library.GeTiNsTaNcE();
		state = controlState.initialised;
	}
	
	
	public void setUI(ReturnBookUI Ui) {
		if (!state.equals(controlState.initialised)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.Ui = Ui;
		uI.setState(ReturnBookUI.uiState.READY);
		state = controlStateREADY;		
	}


	public void bookScanned(int bookId) {
		if (!state.equals(controlState.ready)) 
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		
		Book currentBook = library.getBook(bookId);
		
		if (currentBook == null) {
			Ui.display("Invalid Book Id");
			return;
		}
		if (!currentBook.isOnLoan()) {
			Ui.display("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByBookId(bookId);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue()) 
			overDueFine = library.calculateOverDueFine(currentLoan);
		
		Ui.display("Inspecting");
		Ui.display(currentBook.toString());
		Ui.display(currentLoan.toString());
		
		if (currentLoan.isOverDue()) 
			Ui.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		
		Ui.setState(ReturnBookUI.uiState.inspecting);
		state = controlState.inspecting;		
	}


	public void scanningComplete() {
		if (!state.equals(controlState.ready)) 
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
			
		Ui.setState(ReturnBookUI.uiState.completed);		
	}


	public void dIsChArGe_lOaN(boolean iS_dAmAgEd) {
		if (!state.equals(controlState.inspecting)) 
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		
		library.DiScHaRgE_LoAn(currentLoan, iS_dAmAgEd);
		currentLoan = null;
		Ui.sEt_sTaTe(ReturnBookUI.uI_sTaTe.ready);
		state = controlState.ready;				
	}


}
