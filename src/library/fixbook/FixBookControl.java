package library.fixbook;
import library.entities.Book;
import library.entities.Library;

public class FixBookControl {
	
	private FixBookUi ui;
	private enum CoNtRoL_StAtE { INITIALISED, READY, FIXING };
	private ControlState state;
	
	private Library library;
	private Book currentBook;


	public fixBookControl() {			//fIX_bOOK_cONTROL-->fixBookControl
		this.library = library.getInstance();		//GeTiNsTaNcE --> getInstance
		state = CoNtRoL_StAtE.INITIALISED;		//StAtE-->state
	}
	
	
	public void setUi(FixBookUi ui) {
		if (!state.equals(CoNtRoL_StAtE.INITIALISED)) 
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
			
		this.Ui = ui;
		ui.setState(FixBookUI.uiState.READY);
		state = CoNtRoL_StAtE.READY;		//StAtE --> state		
	}


	public void bookScanned(int bookId) {		//BoOkId --> bookId
		if (!state.equals(CoNtRoL_StAtE.READY)) 
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
			
		currentBook = library.getBook(bookId);		//LiBrArY-->library, BoOkId --> bookId, CuRrEnT_BoOk = currentBook
		
		if (currentBook == null) {			//CuRrEnT_BoOk --> currentBook
			Ui.display("Invalid bookId");		//dIsPlAy --> display
			return;
		}
		if (!currentBook.isDamaged()) {		//iS_DaMaGeD --> isDamaged
			Ui.display("Book has not been damaged");
			return;
		}
		Ui.display(currentBook.toString());
		Ui.setState(FixBookUI.uiState.FIXING);
		state = CoNtRoL_StAtE.FIXING;		
	}


	public void fixBook(boolean mustFix) {		//FiX_BoOk-->fixBook
		if (!state.equals(CoNtRoL_StAtE.FIXING)) 
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
			
		if (mustFix) 
			library.repairBook(currentBook);		//RePaIr_BoOk --> repairBook
		
		currentBook = null;
		Ui.setState(FixBookUI.uI_sTaTe.READY);
		state = CoNtRoL_StAtE.READY;		
	}

	
	public void SCannING_COMplete() {
		if (!state.equals(CoNtRoL_StAtE.READY)) 
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
			
		Ui.SeT_StAtE(FixBookUI.uI_sTaTe.COMPLETED);		
	}

}
