package library.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	//Change variable names according to java style guide
	private static final String libraryFile = "library.obj";  //lIbRaRyFile --->libraryFile
	private static final int loanLimit = 2;		//lOaNlImIt-->loanLimit
	private static final int loanPeriod = 2;
	private static final double finePerDay = 1.0;		//FiNe_PeR_DaY-->finePerDay
	private static final double maxFinesOwed = 1.0;
	private static final double damageFee = 2.0;
	
	private static Library self;		//SeLf --> self
	private int bookId;		//bOoK_Id --> bookId
	private int memberId;		//mEmBeR_Id-->memberId
	private int loanId;		//lOaN_Id-->loanId
	private Date loanDate;			//lOaN_DaTe-->loanDate
	
	private Map<Integer, Book> catalog;		//CaTaLoG --> catalog
	private Map<Integer, Member> members;	//MeMbErS -->members
	private Map<Integer, Loan> loans;		//LoAnS --> loans
	private Map<Integer, Loan> currentLoans;	//CuRrEnT_LoAnS --> currentLoans
	private Map<Integer, Book> damagedBooks;	//DaMaGeD_BoOkS --> damagedBooks
	

	private Library() {
		catalog = new HashMap<>();		//CaTaLoG -->catalog
		members = new HashMap<>();		//MeMbErS --> members
		loans = new HashMap<>();		//LoAnS --> loans
		currentLoans = new HashMap<>();		//CuRrEnT_LoAnS-->currentLoans
		damagedBooks = new HashMap<>();		//DaMaGeD_BoOkS -->damagedBooks
		bookId = 1;			//bOoK_Id --> bookId
		memberId = 1;		//mEmBeR_Id --> memberId		
		loanId = 1;			//lOaN_Id --> loanId		
	}

	
	public static synchronized Library getInstance() {		//GeTiNsTaNcE-->getInstance
		if (self == null) {							//SeLf -->self
			Path PATH = Paths.get(libraryFile);			//lIbRaRyFiLe -->libraryFile			
			if (Files.exists(PATH)) {	
				try (ObjectInputStream libraryFile = new ObjectInputStream(new FileInputStream(libraryFile));) {	////lIbRaRyFiLe -->libraryFile
			    
					self = (Library) libraryFile.readObject();    //lIbRaRyFiLe -->libraryFile , Self--> self
					Calendar.getInstance().setDate(self.loanDate); 	//GeTiNsTaNcE-->getInstance, SeLf.lOaN_DaTe-->self.loanDate, SeT_DaTe-->setDate
					libraryFile.close();		//LiBrArY_FiLe-->libraryFile
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new Library();		//SeLf -->self
		}
		return self;			//SeLf-->self
	}

	
	public static synchronized void save() 		//SaVe-->save
		if (self != null) {			//SeLf -->self
			self.loanDate = Calendar.getInstance().getDate();		//SeLf.lOaN_DaTe-->self.loanDate, gEtInStAnCe().gEt_DaTe()-->getInstance().getDate()
			try (ObjectOutputStream libraryFiles = new ObjectOutputStream(new FileOutputStream(libraryFile));) {
				libraryFiles.writeObject(self);			//LiBrArY_fIlE --libraryFiles
				libraryFiles.flush();
				libraryFiles.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int getBookId() {		//gEt_BoOkId-->getBookId
		return bookId;		//bOoK_Id-->bookId
	}
	
	
	public int getMemberId() {		//gEt_MeMbEr_Id-->getMemberId
		return memberId;		//mEmBeR_Id-->memberId
	}
	
	
	private int getNextBookId() {		//gEt_NeXt_BoOk_Id-->getNextBookId
		return bookId++;		//bOoK_Id-->bookId
	}

	
	private int getNextMemberId() {		//gEt_NeXt_MeMbEr_Id-->getNextMemberId
		return memberId++;		//mEmBeR_Id++-->memberId++
	}

	private int getNextLoanId() {		//gEt_NeXt_LoAn_Id --> getNextLoanId
		return loanId++;				//lOaN_Id -->loanId
	}

	
	public List<Member> listMembers() {		//lIsT_MeMbErS --> listMembers
		return new ArrayList<Member>(members.values()); 	//MeMberS --> members	
	}


	public List<Book> lIsT_BoOkS() {		
		return new ArrayList<Book>(catalog.values()); 	//CaTaLoG -->catalog
	}


	public List<Loan> lISt_CuRrEnT_LoAnS() {
		return new ArrayList<Loan>(currentLoans.values());	//CuRrEnT_LoAnS-->currentLoans
	}


	public Member aDd_MeMbEr(String lastName, String firstName, String email, int phoneNo) {		
		Member member = new Member(lastName, firstName, email, phoneNo, gEt_NeXt_MeMbEr_Id());
		members.put(member.GeT_ID(), member);		//MeMbErS-->members		
		return member;
	}

	
	public Book aDd_BoOk(String a, String t, String c) {		
		Book b = new Book(a, t, c, gEt_NeXt_BoOk_Id());
		catalog.put(b.gEtId(), b);		//CaTaLoG -->catalog
		return b;
	}

	
	public Member gEt_MeMbEr(int memberId) {
		if (members.containsKey(memberId)) 			//MeMbErs --> members
			return members.get(memberId);
		return null;
	}

	
	public Book gEt_BoOk(int bookId) {
		if (CaTaLoG.containsKey(bookId)) 
			return CaTaLoG.get(bookId);		
		return null;
	}

	
	public int gEt_LoAn_LiMiT() {
		return lOaNlImIt;
	}

	
	public boolean cAn_MeMbEr_BoRrOw(Member member) {		
		if (member.gEt_nUmBeR_Of_CuRrEnT_LoAnS() == lOaNlImIt ) 
			return false;
				
		if (member.FiNeS_OwEd() >= maxFinesOwed) 
			return false;
				
		for (Loan loan : member.GeT_LoAnS()) 
			if (loan.Is_OvEr_DuE()) 
				return false;
			
		return true;
	}

	
	public int gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr(Member MeMbEr) {		
		return lOaNlImIt - MeMbEr.gEt_nUmBeR_Of_CuRrEnT_LoAnS();
	}

	
	public Loan iSsUe_LoAn(Book book, Member member) {
		Date dueDate = Calendar.gEtInStAnCe().gEt_DuE_DaTe(loanPeriod);
		Loan loan = new Loan(gEt_NeXt_LoAn_Id(), book, member, dueDate);
		member.TaKe_OuT_LoAn(loan);
		book.BoRrOw();
		LoAnS.put(loan.GeT_Id(), loan);
		CuRrEnT_LoAnS.put(book.gEtId(), loan);
		return loan;
	}
	
	
	public Loan GeT_LoAn_By_BoOkId(int bookId) {
		if (CuRrEnT_LoAnS.containsKey(bookId)) 
			return CuRrEnT_LoAnS.get(bookId);
		
		return null;
	}

	
	public double CaLcUlAtE_OvEr_DuE_FiNe(Loan LoAn) {
		if (LoAn.Is_OvEr_DuE()) {
			long DaYs_OvEr_DuE = Calendar.gEtInStAnCe().GeT_DaYs_DiFfErEnCe(LoAn.GeT_DuE_DaTe());
			double fInE = DaYs_OvEr_DuE * FiNe_PeR_DaY;
			return fInE;
		}
		return 0.0;		
	}


	public void DiScHaRgE_LoAn(Loan cUrReNt_LoAn, boolean iS_dAmAgEd) {
		Member mEmBeR = cUrReNt_LoAn.GeT_MeMbEr();
		Book bOoK  = cUrReNt_LoAn.GeT_BoOk();
		
		double oVeR_DuE_FiNe = CaLcUlAtE_OvEr_DuE_FiNe(cUrReNt_LoAn);
		mEmBeR.AdD_FiNe(oVeR_DuE_FiNe);	
		
		mEmBeR.dIsChArGeLoAn(cUrReNt_LoAn);
		bOoK.ReTuRn(iS_dAmAgEd);
		if (iS_dAmAgEd) {
			mEmBeR.AdD_FiNe(damageFee);
			DaMaGeD_BoOkS.put(bOoK.gEtId(), bOoK);
		}
		cUrReNt_LoAn.DiScHaRgE();
		CuRrEnT_LoAnS.remove(bOoK.gEtId());
	}


	public void cHeCk_CuRrEnT_LoAnS() {
		for (Loan lOaN : CuRrEnT_LoAnS.values()) 
			lOaN.cHeCk_OvEr_DuE();
				
	}


	public void RePaIr_BoOk(Book cUrReNt_BoOk) {
		if (DaMaGeD_BoOkS.containsKey(cUrReNt_BoOk.gEtId())) {
			cUrReNt_BoOk.RePaIr();
			DaMaGeD_BoOkS.remove(cUrReNt_BoOk.gEtId());
		}
		else 
			throw new RuntimeException("Library: repairBook: book is not damaged");
		
		
	}
	
	
}
