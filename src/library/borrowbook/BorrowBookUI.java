package library.borrowbook;
import java.util.Scanner;

//Author: Anish Baniya
//Mediator: Saroj Maharjan
//Reviewer: Roshan Karki


public class BorrowBookUI {
	
	//the static class and uI_STaTe changed to UIState
	public static enum UIState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };

	
	//private bORROW_bOOK_cONTROL CoNtRoL;
	private BorrowBookControl control;
	//private Scanner InPuT;
	private Scanner input;
	//private uI_STaTe StaTe;
	private UIState state;

	
	public BorrowBookUI(BorrowBookControl control) {
		this.control = control;  //changed the case fo control
		this.input = new Scanner(System.in);  //added [this.]
		this.state = UIState.INITIALISED;  //added [this.]
		this.control.setUI(this);  //added [this.]
	}

	
	private String input(String prompt) {  //changed the lettering od prompt
		System.out.print(prompt);
		return this.input.nextLine();  //[this.] 
	}	
		
		
	private void output(Object object) { //changed to output
		System.out.println(object);
	}
	
			
	public void setState(UIState state) { //changed to setState
		this.state = state; //
	}

	
	public void run() { //RuN to run
		this.output("Borrow Book Use Case UI\n"); //OuTpUt
		
		while (true) {
			
			switch (this.state) {	//StaTe		
			
			case CANCELLED:
				this.output("Borrowing Cancelled"); //OuTpUt
				return;

				
			case READY: //WORK ON THIS
				String MEM_STR = iNpUT("Swipe member card (press <enter> to cancel): ");
				if (MEM_STR.length() == 0) {
					this.control.cancel();//CoNtRoL.CaNcEl
					break;
				}
				try {
					int MeMbEr_Id = Integer.valueOf(MEM_STR).intValue();
					CoNtRoL.SwIpEd(MeMbEr_Id);
				}
				catch (NumberFormatException e) {
					OuTpUt("Invalid Member Id");
				}
				break;

				
			case RESTRICTED:
				iNpUT("Press <any key> to cancel");
				CoNtRoL.CaNcEl();
				break;
			
				
			case SCANNING:
				String BoOk_StRiNg_InPuT = iNpUT("Scan Book (<enter> completes): ");
				if (BoOk_StRiNg_InPuT.length() == 0) {
					CoNtRoL.CoMpLeTe();
					break;
				}
				try {
					int BiD = Integer.valueOf(BoOk_StRiNg_InPuT).intValue();
					CoNtRoL.ScAnNeD(BiD);
					
				} catch (NumberFormatException e) {
					OuTpUt("Invalid Book Id");
				} 
				break;
					
				
			case FINALISING:
				String AnS = iNpUT("Commit loans? (Y/N): ");
				if (AnS.toUpperCase().equals("N")) {
					CoNtRoL.CaNcEl();
					
				} else {
					CoNtRoL.CoMmIt_LoAnS();
					iNpUT("Press <any key> to complete ");
				}
				break;
				
				
			case COMPLETED:
				OuTpUt("Borrowing Completed");
				return;
	
				
			default:
				OuTpUt("Unhandled state");
				throw new RuntimeException("BorrowBookUI : unhandled state :" + StaTe);			
			}
		}		
	}


	public void DiSpLaY(Object object) {
		OuTpUt(object);		
	}


}
