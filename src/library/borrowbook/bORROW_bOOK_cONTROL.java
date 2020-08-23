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
		if (lIbRaRy.cAn_MeMbEr_BoRrOw(mEmBeR)) {
			pEnDiNg_LiSt = new ArrayList<>();
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.SCANNING);
			sTaTe = CONTROL_STATE.SCANNING; 
		}
		else {
			uI.DiSpLaY("Member cannot borrow at this time");
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.RESTRICTED); 
		}
	}
	
	
	public void ScAnNeD(int bOoKiD) {
		bOoK = null;
		if (!sTaTe.equals(CONTROL_STATE.SCANNING)) 
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
			
		bOoK = lIbRaRy.gEt_BoOk(bOoKiD);
		if (bOoK == null) {
			uI.DiSpLaY("Invalid bookId");
			return;
		}
		if (!bOoK.iS_AvAiLaBlE()) {
			uI.DiSpLaY("Book cannot be borrowed");
			return;
		}
		pEnDiNg_LiSt.add(bOoK);
		for (Book B : pEnDiNg_LiSt) 
			uI.DiSpLaY(B.toString());
		
		if (lIbRaRy.gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr(mEmBeR) - pEnDiNg_LiSt.size() == 0) {
			uI.DiSpLaY("Loan limit reached");
			CoMpLeTe();
		}
	}
	
	
	public void CoMpLeTe() {
		if (pEnDiNg_LiSt.size() == 0) 
			CaNcEl();
		
		else {
			uI.DiSpLaY("\nFinal Borrowing List");
			for (Book bOoK : pEnDiNg_LiSt) 
				uI.DiSpLaY(bOoK.toString());
			
			cOmPlEtEd_LiSt = new ArrayList<Loan>();
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.FINALISING);
			sTaTe = CONTROL_STATE.FINALISING;
		}
	}


	public void CoMmIt_LoAnS() {
		if (!sTaTe.equals(CONTROL_STATE.FINALISING)) 
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
			
		for (Book B : pEnDiNg_LiSt) {
			Loan lOaN = lIbRaRy.iSsUe_LoAn(B, mEmBeR);
			cOmPlEtEd_LiSt.add(lOaN);			
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
