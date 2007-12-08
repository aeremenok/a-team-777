package ru.spb.messages;

import java.io.Serializable;

/**
 * сериализуемое сообщение одной строкой
 * 
 * @author eav
 * 
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1493421981516237090L;
	private String _message;

	public String getMesage() {
		return _message;
	}

	public void setMesage(String message) {
		this._message = message;
	}

	public String concat(String str) {
		return _message.concat(str);
	}
}
