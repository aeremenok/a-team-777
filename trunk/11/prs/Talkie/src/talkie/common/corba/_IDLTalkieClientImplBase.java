package talkie.common.corba;


/**
* talkie/common/corba/_IDLTalkieClientImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from talkie.idl
* 12 ������� 2008 �. 20:13:06 MSK
*/

public abstract class _IDLTalkieClientImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements talkie.common.corba.IDLTalkieClient, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _IDLTalkieClientImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getLogin", new java.lang.Integer (0));
    _methods.put ("getPass", new java.lang.Integer (1));
    _methods.put ("deliverMessage", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // corba/IDLTalkieClient/getLogin
       {
         String $result = null;
         $result = this.getLogin ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // corba/IDLTalkieClient/getPass
       {
         String $result = null;
         $result = this.getPass ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // corba/IDLTalkieClient/deliverMessage
       {
         String message = in.read_wstring ();
         this.deliverMessage (message);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/IDLTalkieClient:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _IDLTalkieClientImplBase
