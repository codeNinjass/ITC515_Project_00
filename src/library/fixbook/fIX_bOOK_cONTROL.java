package library.fixbook;
import library.entities.Book;
import library.entities.Library;

public class fIX_bOOK_cONTROL {
	
	private FixBookUi ui;
	private enum CoNtRoL_StAtE { INITIALISED, READY, FIXING };
	private ControlState state;
	
	private Library library;
	private Book currentBook;


	public fIX_bOOK_cONTROL() {
		this.library = library.GeTiNsTaNcE();
		state = CoNtRoL_StAtE.INITIALISED;		//StAtE-->state
	}
	
	
	public void SeT_Ui(FixBookUi ui) {
		if (!state.equals(CoNtRoL_StAtE.INITIALISED)) 
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
			
		this.Ui = ui;
		ui.SeT_StAtE(FixBookUI.uI_sTaTe.READY);
		state = CoNtRoL_StAtE.READY;		//StAtE --> state		
	}


	public void BoOk_ScAnNeD(int bookId) {		//BoOkId --> bookId
		if (!state.equals(CoNtRoL_StAtE.READY)) 
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
			
		currentBook = library.gEt_BoOk(bookId);		//LiBrArY-->library, BoOkId --> bookId, CuRrEnT_BoOk = currentBook
		
		if (currentBook == null) {			//CuRrEnT_BoOk --> currentBook
			Ui.dIsPlAy("Invalid bookId");
			return;
		}
		if (!currentBook.iS_DaMaGeD()) {
			Ui.dIsPlAy("Book has not been damaged");
			return;
		}
		Ui.dIsPlAy(currentBook.toString());
		Ui.SeT_StAtE(FixBookUI.uI_sTaTe.FIXING);
		state = CoNtRoL_StAtE.FIXING;		
	}


	public void FiX_BoOk(boolean mustFix) {
		if (!state.equals(CoNtRoL_StAtE.FIXING)) 
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
			
		if (mustFix) 
			library.RePaIr_BoOk(CuRrEnT_BoOk);
		
		CuRrEnT_BoOk = null;
		Ui.SeT_StAtE(FixBookUI.uI_sTaTe.READY);
		StAtE = CoNtRoL_StAtE.READY;		
	}

	
	public void SCannING_COMplete() {
		if (!StAtE.equals(CoNtRoL_StAtE.READY)) 
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
			
		Ui.SeT_StAtE(FixBookUI.uI_sTaTe.COMPLETED);		
	}

}
