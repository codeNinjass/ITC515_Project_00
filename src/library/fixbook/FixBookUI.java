package library.fixbook;	// define package name
import java.util.Scanner;	//Import scanner class


public class FixBookUI {  // define class called FixBookUI

	public static enum Ustate { INITIALISED, READY, FIXING, COMPLETED };	//u_sTaTe -->Ustate

	private FixBookControl control; 
	private Scanner input;	
	private UiState state;	


	
	public FixBookUi(FixBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED;
		control.setUi(this);	//SeT_Ui --> setUi
	}


	public void setState(UiState state) {		//Change function name to universal adopted form. Set_StAte --> setState
		this.state = state;
	}

	
	public void run() {		//Change function name to universal adopted form. RuN --> run
		output("Fix Book Use Case UI\n");
		
		while (true) {
			switch (state) {
			case READY:
				String bookEntryString = input("Scan Book (<enter> completes): ");	//BoOk_EnTrY_StRiNg-->bookEntryString
				if (bookEntryString.length() == 0) {}
					control.scanningComplete();	
				}
				
				else {
					try {
						int bookId = Integer.valueOf(bookEntryString).intValue();
						control.BoOk_ScAnNeD(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");	//OuTpUt --> output
					}
				}
				break;	
				
			case FIXING:
				String ans = input("Fix Book? (Y/N) : ");	//iNpUt --> input
				boolean fix = false;
				if (ans.toUpperCase().equals("Y")) 
					fix = true;
				control.FiX_BoOk(fix);
				break;
								
			case COMPLETED:
				output("Fixing process complete");	//OuTpUt --> output
				return;
			
			default:
				output("Unhandled state");		//OuTpUt --> output
				throw new RuntimeException("FixBookUI : unhandled state :" + state);			
			
			}		
		}
		
	}

	
	private String input(String prompt) {		//change function name to universal addopted naming form. iNpUt -->input
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {		// change function name to universal addopted naming form. OutpUt -->output
		System.out.println(object);
	}
	

	public void display(Object object) {		//change function name to universal addopted naming form. dIsPlAy -->display
		output(object);
	}
	
	
}
