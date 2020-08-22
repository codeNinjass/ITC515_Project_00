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

				
			case READY: 
				String memStr = input("Swipe member card (press <enter> to cancel): ");//MEM_STR->memStr..input
				if (memStr.length() == 0) {
					this.control.cancel();//CoNtRoL.CaNcEl
					break;
				}
				try {
					int memberId = Integer.valueOf(memStr).intValue(); //MeMbEr_Id->memberId
					this.control.swipedmemStr(memberId); //CoNtRoL.SwIpEd(MeMbEr_Id);
				}
				catch (NumberFormatException e) { 
					this.output("Invalid Member Id");
				}
				break;

				
			case RESTRICTED:
				this.input("Press <any key> to cancel"); //iNpUt
				this.control.cancel(); //
				break;
			
				
			case SCANNING:
				String scanBook = input("Scan Book (<enter> completes): "); //BoOk_StRiNg_InPuT->scanBook
				if (scanBook.length() == 0) {
					this.control.complete(); //
					break;
				}
				try {
					int bookId = Integer.valueOf(scanBook).intValue();
					this.control.scanned(bookId); //CoNtRoL.ScAnNeD->this.control.scanned
					
				} catch (NumberFormatException e) {
					this.output("Invalid Book Id"); // added [this] to oUtPut
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
