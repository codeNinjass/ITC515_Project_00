//import packages 
package library.returnBook;
import java.util.Scanner;

/*
Author: Saroj
Mediator: Anish
Reviewer: Bednidhi
*/

public class ReturnBookUi {

	// static class
	public static enum UiState { Initialised, Ready, Inspecting, Completed };

	private returnBookControl control;
	private Scanner input;
	private uiState state;

		
	public returnBookUi(returnBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.Initialised;
		control.setUi(this);
	}


	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case Initialised:
				break;
				
			case Ready:
				String bookInputString = input("Scan Book (<enter> completes): ");
				if (bookInputString.length() == 0) 
					control.scanningComplete();
				
				else {
					try {
						int bookId = Integer.valueOf(bookInputString).intValue();
						control.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case Inspecting:
				String ans = input("Is book damaged? (Y/N): ");
				boolean isDamaged = false;
				if (ans.toUpperCase().equals("Y")) 					
					isDamaged = true;
				
				control.dischargeLoan(isDamaged);
			
			case Completed:
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
