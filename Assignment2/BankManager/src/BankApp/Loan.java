package BankApp;


/**
* BankApp/Loan.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Bank/src/idl/BankApp.idl
* Sunday, November 1, 2015 9:45:01 AM EST
*/

public final class Loan implements org.omg.CORBA.portable.IDLEntity
{
  public String ID = null;
  public String accountNumber = null;
  public int amount = (int)0;
  public int dueDate = (int)0;

  public Loan ()
  {
  } // ctor

  public Loan (String _ID, String _accountNumber, int _amount, int _dueDate)
  {
    ID = _ID;
    accountNumber = _accountNumber;
    amount = _amount;
    dueDate = _dueDate;
  } // ctor

} // class Loan
