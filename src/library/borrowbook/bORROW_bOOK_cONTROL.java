package library.borrowbook;
import java.util.ArrayList;
import java.util.List;

//Author: Anish Baniya
//Mediator: Saroj Maharjan
//Reviewer: Roshan Karki

import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;
import library.entities.Member;

public class BorrowBookControl {  //bORROW_bOOK_cONTROL
	
	private BorrowBookUI UI; //uI
	
	private Library library; //lIbRaRy
	private Member member; //mEmBeR
	private enum ControlState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };//CONTROL_STATE->ControlState
	private ControlState state; //CONTROL_STATE sTaTe->ControlState state
	
	private List<Book> pending; //pEnDiNg_LiSt
	private List<Loan> completed; //cOmPlEtEd_LiSt
	private Book book; //
	
	
	public BorrowBookControl() { //bORROW_bOOK_cONTROL
		this.library = library.getInstance(); //GeTiNsTaNcE
		this.state = ControlState.INITIALISED; //
	}
	

	public void setUI(BorrowBookUI UI) {
		if (!this.state.equals(ControlState.INITIALISED)){ //
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.UI = UI;//
		UI.setState(BorrowBookUI.UIState.READY);//
		this.state = ControlState.READY;//
		}//added {} for this if condition
	}

		
	public void swiped(int memberId) { //chaned swiped and memberId
		if (!this.state.equals(ControlState.READY)) //
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		this.member = library.MEMBER(memberId);//
		if (this.member == null) {//
			this.UI.display("Invalid memberId");//
			return;
		}
		if (this.library.MEMBER_CAN_BORROW(this.member)) { //lIbRaRy.cAn_MeMbEr_BoRrOw
			this.pending = new ArrayList<>(); //pEnDiNg_LiSt
			this.UI.setState(BorrowBookUI.UIState.SCANNING);//
			this.state = ControlState.SCANNING; 
		}
		else {
			this.UI.display("Member cannot borrow at this time"); //uI.DiSpLaY
			this.UI.setState(BorrowBookUI.UIState.RESTRICTED); //uI.SeT_StAtE
		}
	}
	
	
	public void scanned(int bookId) {//channged the variable
		this.book = null;//
		if (!this.state.equals(ControlState.SCANNING)) //
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
			
		this.book = this.library.Book(bookId);//
		if (this.book == null) {
			this.UI.display("Invalid bookId");//
			return;
		}
		if (!this.book.AVAILABLE()) {//
			this.UI.display("Book cannot be borrowed");// B->displayBook
			return;
		}
		this.pending.add(this.book);//
		for (book displayBook : this.pending) //
			this.UI.display(displayBook.toString()); //
		
		if (this.library.Loans_Remaining_For_Member(this.member) - this.pending.size() == 0) { //gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr-->Loans_Remaining_For_Member
			this.UI.display("Loan limit reached");//
			this.complete();//
		}
	}
	
	
	public void complete() { //changed to complete
		if (this.pending.size() == 0) //changed to pending
			this.cancel();
		
		else {
			this.UI.display("\nFinal Borrowing List");//
			for (book displayBook : this.pending) {
				this.UI.display(displayBook.toString());//
			}
			
			this.completed = new ArrayList<Loan>();//changed to completed
			this.UI.setState(BorrowBookUI.UIState.FINALISING);//changed to BorrowBookUI
			this.state = ControlState.FINALISING;//changed to ControlState
		}
	}


	public void commitLoans() { //changed to commitLoans
		if (!this.state.equals(ControlState.FINALISING)){ //
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}
		for (book displayBook : this.pending) { //changed to displayBook and pending
			loan LOAN = this.library.ISSUE_LOAN(displayBook, this.member); //changed to LOAN, displayBook and this.member
			this.completed.add(LOAN); //changed to completed			
		}
		uI.DiSpLaY("Completed Loan Slip");
		for (Loan LOAN : cOmPlEtEd_LiSt) 
			uI.DiSpLaY(LOAN.toString());
		
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.COMPLETED);
		sTaTe = CONTROL_STATE.COMPLETED;
	}

	
	public void CaNcEl() {
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.CANCELLED);
		sTaTe = CONTROL_STATE.CANCELLED;
	}
	
	
}
