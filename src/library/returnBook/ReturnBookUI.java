//import packages 
package library.returnBook;
import java.util.Scanner;

public class ReturnBookUI {

	// static class
	public static enum uiState { INITIALISED, READY, INSPECTING, COMPLETED };

	private returnBookControl control;
	private Scanner input;
	private uiState state;

	
	public ReturnBookUI(returnBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = uiState.INITIALISED;
		control.setUi(this);
	}


	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case INITIALISED:
				break;
				
			case READY:
				String BoOk_InPuT_StRiNg = input("Scan Book (<enter> completes): ");
				if (BoOk_InPuT_StRiNg.length() == 0) 
					control.sCaNnInG_cOmPlEtE();
				
				else {
					try {
						int Book_Id = Integer.valueOf(BoOk_InPuT_StRiNg).intValue();
						control.bOoK_sCaNnEd(Book_Id);
					}
					catch (NumberFormatException e) {
						oUtPuT("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String AnS = input("Is book damaged? (Y/N): ");
				boolean Is_DAmAgEd = false;
				if (AnS.toUpperCase().equals("Y")) 					
					Is_DAmAgEd = true;
				
				control.dIsChArGe_lOaN(Is_DAmAgEd);
			
			case COMPLETED:
				output("Return processing complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + StATe);			
			}
		}
	}

	
	private String input(String PrOmPt) {
		System.out.print(PrOmPt);
		return input.nextLine();
	}	
		
		
	private void output(Object ObJeCt) {
		System.out.println(ObJeCt);
	}
	
			
	public void DiSpLaY(Object object) {
		output(object);
	}
	
	public void setState(uI_sTaTe state) {
		this.state = state;
	}

	
}
