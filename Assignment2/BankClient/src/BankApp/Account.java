package BankApp;


/**
* BankApp/Account.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/BankApp.idl
* Saturday, October 31, 2015 11:00:25 AM EDT
*/

public final class Account implements org.omg.CORBA.portable.IDLEntity
{
  public String accountNumber = null;
  public String firstName = null;
  public String lastName = null;
  public String emailAddress = null;
  public String phoneNumber = null;
  public String password = null;
  public int creditLimit = (int)0;

  public Account ()
  {
  } // ctor

  public Account (String _accountNumber, String _firstName, String _lastName, String _emailAddress, String _phoneNumber, String _password, int _creditLimit)
  {
    accountNumber = _accountNumber;
    firstName = _firstName;
    lastName = _lastName;
    emailAddress = _emailAddress;
    phoneNumber = _phoneNumber;
    password = _password;
    creditLimit = _creditLimit;
  } // ctor

} // class Account