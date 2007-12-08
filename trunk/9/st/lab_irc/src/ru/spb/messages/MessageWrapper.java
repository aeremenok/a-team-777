package ru.spb.messages;

/**
 * обертка сообщения, позволяющая обращаться к отдельным полям
 * 
 * @author eav
 */
public class MessageWrapper {

	private String prefix;
	private String command;
	private String params;
	private byte nospcrlfcl;
	private String middle;
	private String trailing;
	public final String SPACE = "%x20";
	public final String CRLF = "%x0D %x0A";

	public MessageWrapper(Message message) {
		wrapMessage(message);
	}

	/**
	 * преобразовать поля в непрерывное сообщение
	 * 
	 * @return сообщение, годное к отправке
	 */
	public Message unwrapMessage() {
		Message res = new Message();
		res.concat(prefix);
		// ...
		return res;
	}

	/**
	 * разобрать сообщение по полям
	 * 
	 * @param message
	 *            исходное сообщение
	 */
	public void wrapMessage(Message message) {
		// ?
	}
}
