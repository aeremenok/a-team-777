package talkie.common.corba;


/**
* talkie/common/corba/IDLTalkieServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from talkie.idl
* 12 ������� 2008 �. 20:22:16 MSK
*/

public interface IDLTalkieServerOperations 
{
  boolean login (talkie.common.corba.IDLTalkieClient client);
  void logout (String login);
  void postMessage (String login, String message);
  void whoIsHere (String login);
} // interface IDLTalkieServerOperations
