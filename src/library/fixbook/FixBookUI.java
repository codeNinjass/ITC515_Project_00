package library.fixbook;	// define package name
import java.util.Scanner;	//Import scanner class


public class FixBookUI {  // define class called FixBookUI

	public static enum u_sTaTe { INITIALISED, READY, FIXING, COMPLETED };

	private FixBookControl control; //using standard naming convention for variable name and its data type 
	private Scanner input;	//usiing standard naming convention for variable name and its data type
	private UiState state;	// using standard naming convention for variable name and its data type


	
	public FixBookUI(FixBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED;
		control.SeT_Ui(this);
	}


	public void setState(UiState state) {		//Change function name to universal adopted form. Set_StAte --> setState
		this.state = state;
	}

	
	public void run() {		//Change function name to universal adopted form. RuN --> run
		OuTpUt("Fix Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case READY:
				String BoOk_EnTrY_StRiNg = iNpUt("Scan Book (<enter> completes): ");
				if (BoOk_EnTrY_StRiNg.length() == 0) 
					control.SCannING_COMplete();
				
				else {
					try {
						int BoOk_Id = Integer.valueOf(BoOk_EnTrY_StRiNg).intValue();
						CoNtRoL.BoOk_ScAnNeD(BoOk_Id);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");	//OuTpUt --> output
					}
				}
				break;	
				
			case FIXING:
				String AnS = input("Fix Book? (Y/N) : ");	//iNpUt --> input
				boolean FiX = false;
				if (AnS.toUpperCase().equals("Y")) 
					FiX = true;
				
				control.FiX_BoOk(FiX);
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
