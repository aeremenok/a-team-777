package talkie.common.corba;


/**
* talkie/common/corba/_IDLTalkieServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from talkie.idl
* 12 ������� 2008 �. 20:22:16 MSK
*/

public class _IDLTalkieServerStub extends org.omg.CORBA.portable.ObjectImpl implements talkie.common.corba.IDLTalkieServer
{

  public boolean login (talkie.common.corba.IDLTalkieClient client)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("login", true);
                talkie.common.corba.IDLTalkieClientHelper.write ($out, client);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return login (client        );
            } finally {
                _releaseReply ($in);
            }
  } // login

  public void logout (String login)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("logout", true);
                $out.write_wstring (login);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                logout (login        );
            } finally {
                _releaseReply ($in);
            }
  } // logout

  public void postMessage (String login, String message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("postMessage", true);
                $out.write_wstring (login);
                $out.write_wstring (message);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                postMessage (login, message        );
            } finally {
                _releaseReply ($in);
            }
  } // postMessage

  public void whoIsHere (String login)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("whoIsHere", true);
                $out.write_wstring (login);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                whoIsHere (login        );
            } finally {
                _releaseReply ($in);
            }
  } // whoIsHere

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/IDLTalkieServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _IDLTalkieServerStub
