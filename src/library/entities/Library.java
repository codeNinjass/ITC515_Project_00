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


	public List<Book> listBooks() {		//lIsT_BoOkS --> listBooks	
		return new ArrayList<Book>(catalog.values()); 	//CaTaLoG -->catalog
	}


	public List<Loan> listCurrentLoans() {	//lISt_CuRrEnT_LoAnS -->listCurrentLoans
		return new ArrayList<Loan>(currentLoans.values());	//CuRrEnT_LoAnS-->currentLoans
	}


	public Member addMember(String lastName, String firstName, String email, int phoneNo) {		
		int memId = getNextMemberId(); 
		Member member = new Member(lastName, firstName, email, phoneNo, memId);	//gEt_NeXt_MeMbEr_Id-->getNextMemberId
		int id = member.getId();
		members.put(id, member);		//MeMbErS-->members		
		return member;
	}

	
	public Book addBook(String a, String t, String c) {
		int nextBookId = getNextBookId();		
		Book b = new Book(a, t, c, nextBookId);		//gEt_NeXt_BoOk_Id-->getNextBookId
		int id = b.getId();
		catalog.put(id, b);		//CaTaLoG -->catalog
		return b;
	}

	
	public Member getMember(int memberId) {
		if (members.containsKey(memberId)) 			//MeMbErs --> members
			return members.get(memberId);
		return null;
	}

	
	public Book getBook(int bookId) {
		if (catalog.containsKey(bookId)) 	//CaTaLoG -->catalog
			return catalog.get(bookId);		
		return null;
	}

	
	public int getLoanLimit() {
		return loanLimit;			//lOaNlImIt-->loanLimit
	}

	
	public boolean canMemberBorrow(Member member) {		//cAn_MeMbEr_BoRrOw-->canMemberBorrow	
		if (member.getNumberOfCurrentLoans() == loanLimit )	//gEt_nUmBeR_Of_CuRrEnT_LoAnS-->getNumberOfCurrentLoans 
			return false;
				
		if (member.finesOwned() >= maxFinesOwed) 
			return false;
				
		for (Loan loan : member.getLoans()) 
			if (loan.isOverDue()) 
				return false;
			
		return true;
	}

	
	public int getNumberOfLoansRemainingForMember(Member member) {	//gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr--getNumberOfLoansRemainingForMember		
		return loanLimit - member.getNumberOfCurrentLoans();		//MeMbEr --> member, lOaNlImIt -->loanLimit
	}

	
	public Loan issueLoan(Book book, Member member) {
		Date dueDate = Calendar.getInstance().getDueDate(loanPeriod);
		int nextLoanId = getNextLoanId();
		Loan loan = new Loan(NextLoanId, book, member, dueDate);
		member.takeOutLoan(loan);
		book.borrow();
		int lId = loan.getId();
		loans.put(lId, loan);			//LoAnS --> loans
		int bId = book.getId();
		currentLoans.put(bId, loan);		//CuRrEnT_LoAnS--> currentLoans
		return loan;
	}
	
	
	public Loan getLoanByBookId(int bookId) {
		if (currentLoans.containsKey(bookId)) 			//CuRrEnT_LoAnS --> currentLoans
			return currentLoans.get(bookId);
		
		return null;
	}

	
	public double calculateOverDueFine(Loan loan) {
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.getInstance().getDaysDifference(loan.getDueDate()); 	//DaYs_OvEr_DuE-->daysOverDue
			double fine = daysOverDue * finePerDay;			//fInE --> fine,	FiNe_PeR_DaY-->finePerDay
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(Loan currentLoan, boolean isDamaged) {		//cUrReNt_LoAn-->currentLoan, iS_dAmAgEd-->isDamaged
		Member member = currentLoan.getMember();
		Book book  = currentLoan.getBook();
		
		double overDueFine = calculateOverDueFine(currentLoan);		//cUrReNt_LoAn -->currentLoan, oVeR_DuE_FiNe--> overDueFine
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan);		//cUrReNt_LoAn -->currentLoan
		book.return(isDamaged);
		if (isDamaged) {
			member.AddFine(damageFee);
			int bId = book.getId();
			damagedBooks.put(bId, booK);
		}
		currentLoan.discharge();
		currentLoans.remove(booK.getId());
	}

	//lOaN -->loan  , CuRrEnT_LoAnS-->currentLoans
	public void checkCurrentLoans() {
		for (Loan loan : currentLoans.values()) 
			loan.checkOverDue();
				
	}

//cUrReNt_BoOk --> currentBook, DaMaGeD_BoOkS-->damagedBooks
	public void repairBook(Book currentBook) {
		if (damagedBooks.containsKey(currentBook.getId())) {		
			currentBook.repair();
		    damagedBooks.remove(currentBook.getId());
		}
		else 
			throw new RuntimeException("Library: repairBook: book is not damaged");
	}
	
	
}
