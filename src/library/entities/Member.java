//import packages
package library.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Author: Saroj
Mediator: Anish
Reviewer: Bednidhi
*/

@SuppressWarnings("serial")
public class Member implements Serializable {

	private String lastName;
	private String firstName;
	private String emailAddress;
	private int phoneNumber;
	private int memberId;
	private double finesOwing;
	
	private Map<Integer, Loan> cUrReNt_lOaNs;

	
	public Member(String lastName, String firstName, String emailAddress, int phoneNumber, int memberId) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.memberId = memberId;
		
		this.cUrReNt_lOaNs = new HashMap<>();
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Member:  ").append(
				memberId).append("\n")
		  .append("  Name:  ").append(
						lastName).append(", ").append(
						firstName).append("\n")
		  .append("  Email: ").append(
						emailAddress).append("\n")
		  .append("  Phone: ").append(
						phoneNumber)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", finesOwing))
		  .append("\n");
		
		for (Loan loan : currentLoans.values()) {
			sb.append(loan).append("\n");
		}		  
		return sb.toString();
	}

	
	public int getId() {
		return memberId;
	}

	
	public List<Loan> GeT_LoAnS() {
		return new ArrayList<Loan>(currentLoans.values());
	}

	
	public int gEt_nUmBeR_Of_CuRrEnT_LoAnS() {
		return cUrReNt_lOaNs.size();
	}

	
	public double FiNeS_OwEd() {
		return finesOwing;
	}

	
	public void TaKe_OuT_LoAn(Loan loan) {
		if (!currentloans.containsKey(loan.getId())) 
			currentLoans.put(loan.getId(), loan);
		
		else 
			throw new RuntimeException("Duplicate loan added to member");
				
	}

	
	public String GeT_LaSt_NaMe() {
		return lastName;
	}

	
	public String GeT_FiRsT_NaMe() {
		return firstName;
	}


	public void AdD_FiNe(double fine) {
		finesOwing += fine;
	}
	
	public double PaY_FiNe(double AmOuNt) {
		if (AmOuNt < 0) 
			throw new RuntimeException("Member.payFine: amount must be positive");
		
		double change = 0;
		if (AmOuNt > finesOwing) {
			change = AmOuNt - finesOwing;
			finesOwing = 0;
		}
		else 
			finesOwing -= AmOuNt;
		
		return change;
	}


	public void dIsChArGeLoAn(Loan loan) {
		if (currentLoans.containsKey(loan.getId())) 
			currentLoans.remove(loan.getId());
		
		else 
			throw new RuntimeException("No such loan held by member");
				
	}

}
