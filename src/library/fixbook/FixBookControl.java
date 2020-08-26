package library.fixbook;
import library.entities.Book;
import library.entities.Library;

public class FixBookControl {
	
	private FixBookUi ui;
	private enum ControlState { INITIALISED, READY, FIXING };	//CoNtRoL_StAtE --> ControlState
	private ControlState state;
	
	private Library library;
	private Book currentBook;


	public fixBookControl() {			//fIX_bOOK_cONTROL-->fixBookControl
		this.library = library.getInstance();		//GeTiNsTaNcE --> getInstance
		state = ControlState.INITIALISED;		//StAtE-->state
	}
	
	
	public void setUi(FixBookUi ui) {
		if (!state.equals(ControlState.INITIALISED)) 
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
			
		this.Ui = ui;
		ui.setState(FixBookUI.uiState.READY);
		state = ControlState.READY;		//StAtE --> state		
	}


	public void bookScanned(int bookId) {		//BoOkId --> bookId
		if (!state.equals(ControlState.READY)) 
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
			
		currentBook = library.getBook(bookId);		//LiBrArY-->library, BoOkId --> bookId, CuRrEnT_BoOk = currentBook
		
		if (currentBook == null) {			//CuRrEnT_BoOk --> currentBook
			ui.display("Invalid bookId");		//dIsPlAy --> display
			return;
		}
		if (!currentBook.isDamaged()) {		//iS_DaMaGeD --> isDamaged
			ui.display("Book has not been damaged");
			return;
		}
		Ui.display(currentBook.toString());
		Ui.setState(FixBookUI.uiState.FIXING);
		state = ControlState.FIXING;		
	}


	public void fixBook(boolean mustFix) {		//FiX_BoOk-->fixBook
		if (!state.equals(ControlState.FIXING)) 
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
			
		if (mustFix) 
			library.repairBook(currentBook);		//RePaIr_BoOk --> repairBook
		
		currentBook = null;
		Ui.setState(FixBookUi.uiState.READY);
		state = ControlState.READY;		
	}

	
	public void scanningComplete() {		//SCannING_COMplete --> scanningComplete
		if (!state.equals(ControlState.READY)) 
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
			
		Ui.setState(FixBookUI.uiState.COMPLETED);		
	}

}
