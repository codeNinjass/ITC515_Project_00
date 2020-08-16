// import packages
package library.returnBook;
import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;

public class ReturnBookControl {

	private ReturnBookUI Ui;
	private enum controlState { INITIALISED, READY, INSPECTING };
	private controlState state;
	
	private Library library;
	private Loan currentLoan;
	

	public ReturnBookControl() {
		this.library= Library.GeTiNsTaNcE();
		state = controlState.INITIALISED;
	}
	
	
	public void sEt_uI(ReturnBookUI uI) {
		if (!state.equals(cOnTrOl_sTaTe.INITIALISED)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.Ui = uI;
		uI.sEt_sTaTe(ReturnBookUI.uI_sTaTe.READY);
		state = controlStateREADY;		
	}


	public void bOoK_sCaNnEd(int bOoK_iD) {
		if (!state.equals(controlState.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		
		Book cUrReNt_bOoK = library.gEt_BoOk(bOoK_iD);
		
		if (cUrReNt_bOoK == null) {
			Ui.DiSpLaY("Invalid Book Id");
			return;
		}
		if (!cUrReNt_bOoK.iS_On_LoAn()) {
			Ui.DiSpLaY("Book has not been borrowed");
			return;
		}		
		currentLoan = library.GeT_LoAn_By_BoOkId(bOoK_iD);	
		double Over_Due_Fine = 0.0;
		if (currentLoan.Is_OvEr_DuE()) 
			Over_Due_Fine = library.CaLcUlAtE_OvEr_DuE_FiNe(currentLoan);
		
		Ui.DiSpLaY("Inspecting");
		Ui.DiSpLaY(cUrReNt_bOoK.toString());
		Ui.DiSpLaY(currentLoan.toString());
		
		if (CurrENT_loan.Is_OvEr_DuE()) 
			Ui.DiSpLaY(String.format("\nOverdue fine : $%.2f", Over_Due_Fine));
		
		Ui.sEt_sTaTe(ReturnBookUI.uI_sTaTe.INSPECTING);
		state = controlState.INSPECTING;		
	}


	public void sCaNnInG_cOmPlEtE() {
		if (!state.equals(controlState.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
			
		Ui.sEt_sTaTe(ReturnBookUI.uI_sTaTe.COMPLETED);		
	}


	public void dIsChArGe_lOaN(boolean iS_dAmAgEd) {
		if (!state.equals(controlState.INSPECTING)) 
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		
		library.DiScHaRgE_LoAn(currentLoan, iS_dAmAgEd);
		currentLoan = null;
		Ui.sEt_sTaTe(ReturnBookUI.uI_sTaTe.READY);
		state = controlState.READY;				
	}


}
