package BankApp;


/**
* BankApp/_ClientStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Bank/src/idl/BankApp.idl
* Sunday, November 1, 2015 9:45:01 AM EST
*/

public class _ClientStub extends org.omg.CORBA.portable.ObjectImpl implements BankApp.Client
{

  public String openAccount (String bank, String firstName, String lastName, String emailAddress, String phoneNumber, String password)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("openAccount", true);
                $out.write_string (bank);
                $out.write_string (firstName);
                $out.write_string (lastName);
                $out.write_string (emailAddress);
                $out.write_string (phoneNumber);
                $out.write_string (password);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return openAccount (bank, firstName, lastName, emailAddress, phoneNumber, password        );
            } finally {
                _releaseReply ($in);
            }
  } // openAccount

  public String getLoan (String bank, String accountNumber, String password, int loanAmount)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getLoan", true);
                $out.write_string (bank);
                $out.write_string (accountNumber);
                $out.write_string (password);
                $out.write_long (loanAmount);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getLoan (bank, accountNumber, password, loanAmount        );
            } finally {
                _releaseReply ($in);
            }
  } // getLoan

  public String transferLoan (String loanID, String currentBank, String otherBank)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("transferLoan", true);
                $out.write_string (loanID);
                $out.write_string (currentBank);
                $out.write_string (otherBank);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return transferLoan (loanID, currentBank, otherBank        );
            } finally {
                _releaseReply ($in);
            }
  } // transferLoan

  public String delayPayment (String bank, String loanID, int currentDueDate, int newDueDate)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("delayPayment", true);
                $out.write_string (bank);
                $out.write_string (loanID);
                $out.write_long (currentDueDate);
                $out.write_long (newDueDate);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return delayPayment (bank, loanID, currentDueDate, newDueDate        );
            } finally {
                _releaseReply ($in);
            }
  } // delayPayment

  public String printCustomerInfo (String bank)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("printCustomerInfo", true);
                $out.write_string (bank);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return printCustomerInfo (bank        );
            } finally {
                _releaseReply ($in);
            }
  } // printCustomerInfo

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:BankApp/Client:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ClientStub
