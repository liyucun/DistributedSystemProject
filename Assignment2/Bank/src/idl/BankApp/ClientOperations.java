package BankApp;


/**
* BankApp/ClientOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from BankApp.idl
* Wednesday, October 28, 2015 10:27:06 PM EDT
*/

public interface ClientOperations 
{
  BankApp.Account openAccount (String bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password);
  BankApp.Loan getLoan (String bank, String accountNumber, String password, int loanAmount);
  BankApp.Loan transferLoan (String loanID, String currentBank, String otherBank);
  String delayPayment (String bank, String loanID, int currentDueDate, int newDueDate);
  String printCustomerInfo (String bank);
} // interface ClientOperations