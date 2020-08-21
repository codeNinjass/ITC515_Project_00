// import packages
package library.payfine;
import java.util.Scanner;


public class PayFineUI { // define class name called PayFineUI


	public static enum UiState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

	private PayFineControl control; // standard naming convention for variable name and its data type
	private Scanner input; // standard naming convention for variable name and its data type
	private UiState state;// standard naming convention for variable name and its data type 

	
	public PayFineUI(PayFineControl control) { 
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED;
		control.setUi(this);
	}
	
	
	public void setState(UiState state) { //Change standard function name from SeT_StAtE --> setState
		this.state = state;
	}


	public void run() { //Change standard function name from RuN --> run
		output("Pay Fine Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case READY:
				String memStr = input("Swipe member card (press <enter> to cancel): ");
				if (memStr.length() == 0) {
					control.cancel();
					break;
				}
				try {
					int memberId = Integer.valueOf(memStr).intValue();
					control.CaRd_sWiPeD(memberId);
				}
				catch (NumberFormatException e) {
					output("Invalid memberId");
				}
				break;
				
			case PAYING:
				double amount = 0;
				String amtStr = input("Enter amount (<Enter> cancels) : ");
				if (amtStr.length() == 0) {
					control.cancel();
					break;
				}
				try {
					amount = Double.valueOf(amtStr).doubleValue();
				}
				catch (NumberFormatException e) {}
				if (amount <= 0) {
					output("amount must be positive");
					break;
				}
				control.payFine(amount);
				break;
								
			case CANCELLED:
				output("Pay Fine process cancelled");
				return;
			
			case COMPLETED:
				output("Pay Fine process complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("FixBookUI : unhandled state :" + state);			
			
			}		
		}		
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}	
			

	public void display(Object object) { //Change standard function name from DiSplAY --> display
		output(object);
	}


}
