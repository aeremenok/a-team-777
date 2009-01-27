package talkie.common.corba;


/**
* talkie/common/corba/IDLTalkieClientHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from talkie.idl
* 12 ������� 2008 �. 20:22:16 MSK
*/

abstract public class IDLTalkieClientHelper
{
  private static String  _id = "IDL:corba/IDLTalkieClient:1.0";

  public static void insert (org.omg.CORBA.Any a, talkie.common.corba.IDLTalkieClient that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static talkie.common.corba.IDLTalkieClient extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (talkie.common.corba.IDLTalkieClientHelper.id (), "IDLTalkieClient");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static talkie.common.corba.IDLTalkieClient read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_IDLTalkieClientStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, talkie.common.corba.IDLTalkieClient value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static talkie.common.corba.IDLTalkieClient narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof talkie.common.corba.IDLTalkieClient)
      return (talkie.common.corba.IDLTalkieClient)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      talkie.common.corba._IDLTalkieClientStub stub = new talkie.common.corba._IDLTalkieClientStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static talkie.common.corba.IDLTalkieClient unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof talkie.common.corba.IDLTalkieClient)
      return (talkie.common.corba.IDLTalkieClient)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      talkie.common.corba._IDLTalkieClientStub stub = new talkie.common.corba._IDLTalkieClientStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}