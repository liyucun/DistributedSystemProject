package BankApp;

/**
* BankApp/LoanHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Bank/src/idl/BankApp.idl
* Sunday, November 1, 2015 9:45:01 AM EST
*/

public final class LoanHolder implements org.omg.CORBA.portable.Streamable
{
  public BankApp.Loan value = null;

  public LoanHolder ()
  {
  }

  public LoanHolder (BankApp.Loan initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BankApp.LoanHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BankApp.LoanHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BankApp.LoanHelper.type ();
  }

}
