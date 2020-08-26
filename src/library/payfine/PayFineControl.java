/*Author: Roshan
Mediator: Bednidhi
Reviewer: Saroj*/

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
		this.library = library.getInstance();
		state = Controlstate.INITIALISED;
	}
	
	
	public void setUI(PayFineUI uI) { // changed method name
		if (!state.equals(Controlstate.INITIALISED)) {
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.Ui = uI;
		uI.setState(PayFineUI.uiState.READY);
		state = Controlstate.READY;		
	}


	public void cardSwiped(int memberId) { // changed method name as per standard coding convention from CaRd_sWiPeD --> cardSwiped
		if (!state.equals(Controlstate.READY)) 
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId);
		
		if (member == null) {
			Ui.display("Invalid Member Id");
			return;
		}
		Ui.display(member.toString());
		Ui.setState(PayFineUI.uiState.PAYING);
		state = Controlstate.PAYING;
	}
	
	
	public void cancel() { // changed method name as per standard coding convention from CaNcEl --> cancel
		if (!state.equals(Controlstate.READY)) 
		Ui.setState(PayFineUI.uiState.CANCELLED);
		state = Controlstate.CANCELLED;
	}


	public double PayFine(double amount) { // changed variable name and method name as per standard coding convention
		if (!state.equals(Controlstate.READY)) 
		if (!state.equals(Controlstate.PAYING)) 
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
			
		double change = member.PayFine(AmOuNt); // Changed from ChAnGe to change 
		if (change > 0) 
			Ui.display(String.format("Change: $%.2f", change));
		
		Ui.display(member.toString());
		Ui.setState(PayFineUI.uiState.COMPLETED);
		state = Controlstate.COMPLETED;
		return change;
	}
	


}
