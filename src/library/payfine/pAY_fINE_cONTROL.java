// import packages
package library.payfine;
import library.entities.Library;
import library.entities.Member;

public class PayFineControl { // changing class name as per standard coding convention from pAY_fINE_cONTROL --> PayFineControl
	
	private PayFineUI Ui;
	private enum Controlstate { INITIALISED, READY, PAYING, COMPLETED, CANCELLED }; // changing class name as per standard coding convention from  --> Controlstate
	private Controlstate state; // standard naming convention for variable name and its data type  StAtE
	
	private Library library; // standard naming convention for variable name and its data type  StAtE
	private Member member; // standard naming convention for variable name and its data type  StAtE


	public PayFineControl() {
		this.library = library.GeTiNsTaNcE();
		state = Controlstate.INITIALISED;
	}
	
	
	public void SeT_uI(PayFineUI uI) {
		if (!state.equals(Controlstate.INITIALISED)) {
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.Ui = uI;
		uI.SeT_state(PayFineUI.uI_state.READY);
		state = Controlstate.READY;		
	}


	public void CaRd_sWiPeD(int MeMbEr_Id) {
		if (!state.equals(Controlstate.READY)) 
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
			
		member = library.gEt_MeMbEr(MeMbEr_Id);
		
		if (member == null) {
			Ui.DiSplAY("Invalid Member Id");
			return;
		}
		Ui.DiSplAY(member.toString());
		Ui.SeT_state(PayFineUI.uI_state.PAYING);
		state = Controlstate.PAYING;
	}
	
	
	public void CaNcEl() {
		Ui.SeT_state(PayFineUI.uI_state.CANCELLED);
		state = Controlstate.CANCELLED;
	}


	public double PaY_FiNe(double AmOuNt) {
		if (!state.equals(Controlstate.PAYING)) 
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
			
		double ChAnGe = member.PaY_FiNe(AmOuNt);
		if (ChAnGe > 0) 
			Ui.DiSplAY(String.format("Change: $%.2f", ChAnGe));
		
		Ui.DiSplAY(member.toString());
		Ui.SeT_state(PayFineUI.uI_state.COMPLETED);
		state = Controlstate.COMPLETED;
		return ChAnGe;
	}
	


}
