//import packages 
package library.returnBook;
import java.util.Scanner;

/*
Author: Saroj
Mediator: Anish
Reviewer: Bednidhi
*/

public class ReturnBookUI {

	// static class
	public static enum uiState { initialised, ready, inspecting, completed };

	private returnBookControl control;
	private Scanner input;
	private uiState state;

	
	public ReturnBookUI(returnBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = uiState.initialised;
		control.setUi(this);
	}


	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case initialised:
				break;
				
			case ready:
				String bookInputString = input("Scan Book (<enter> completes): ");
				if (bookInputString.length() == 0) 
					control.sCaNnInG_cOmPlEtE();
				
				else {
					try {
						int bookId = Integer.valueOf(bookInputString).intValue();
						control.bOoK_sCaNnEd(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case inspecting:
				String ans = input("Is book damaged? (Y/N): ");
				boolean isDamaged = false;
				if (ans.toUpperCase().equals("Y")) 					
					isDamaged = true;
				
				control.dIsChArGe_lOaN(isDamaged);
			
			case completed:
				output("Return processing complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + state);			
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
	
			
	public void display(Object object) {
		output(object);
	}
	
	public void setState(uiState state) {
		this.state = state;
	}

	
}
