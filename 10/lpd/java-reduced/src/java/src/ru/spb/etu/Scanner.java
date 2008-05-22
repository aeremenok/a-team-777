package ru.spb.etu;

import java.io.InputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

class Token {
	int kind;    // token kind
	int pos;     // token position in the source text (starting at 0)
	int col;     // token column (starting at 1)
	int line;    // token line (starting at 1)
	String val;  // token value
	Token next;  // ML 2005-03-11 Peek tokens are kept in linked list
}

//-----------------------------------------------------------------------------------
// Buffer
//-----------------------------------------------------------------------------------
class Buffer {
	// This Buffer supports the following cases:
	// 1) seekable stream (file)
	//    a) whole stream in buffer
	//    b) part of stream in buffer
	// 2) non seekable stream (network, console)

	public static final int EOF = Character.MAX_VALUE + 1;
	private static final int MIN_BUFFER_LENGTH = 1024; // 1KB
	private static final int MAX_BUFFER_LENGTH = MIN_BUFFER_LENGTH * 64; // 64KB
	private byte[] buf;   // input buffer
	private int bufStart; // position of first byte in buffer relative to input stream
	private int bufLen;   // length of buffer
	private int fileLen;  // length of input stream (may change if stream is no file)
	private int bufPos;      // current position in buffer
	private RandomAccessFile file; // input stream (seekable)
	private InputStream stream; // growing input stream (e.g.: console, network)

	public Buffer(InputStream s) {
		stream = s;
		fileLen = bufLen = bufStart = bufPos = 0;
		buf = new byte[MIN_BUFFER_LENGTH];
	}

	public Buffer(String fileName) {
		try {
			file = new RandomAccessFile(fileName, "r");
			fileLen = (int) file.length();
			bufLen = Math.min(fileLen, MAX_BUFFER_LENGTH);
			buf = new byte[bufLen];
			bufStart = Integer.MAX_VALUE; // nothing in buffer so far
			if (fileLen > 0) setPos(0); // setup buffer to position 0 (start)
			else bufPos = 0; // index 0 is already after the file, thus setPos(0) is invalid
			if (bufLen == fileLen) Close();
		} catch (IOException e) {
			throw new FatalError("Could not open file " + fileName);
		}
	}

	// don't use b after this call anymore
	// called in UTF8Buffer constructor
	protected Buffer(Buffer b) {
		buf = b.buf;
		bufStart = b.bufStart;
		bufLen = b.bufLen;
		fileLen = b.fileLen;
		bufPos = b.bufPos;
		file = b.file;
		stream = b.stream;
		// keep finalize from closing the file
		b.file = null;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		Close();
	}

	protected void Close() {
		if (file != null) {
			try {
				file.close();
				file = null;
			} catch (IOException e) {
				throw new FatalError(e.getMessage());
			}
		}
	}

	public int Read() {
		if (bufPos < bufLen) {
			return buf[bufPos++] & 0xff;  // mask out sign bits
		} else if (getPos() < fileLen) {
			setPos(getPos());         // shift buffer start to pos
			return buf[bufPos++] & 0xff; // mask out sign bits
		} else if (stream != null && ReadNextStreamChunk() > 0) {
			return buf[bufPos++] & 0xff;  // mask out sign bits
		} else {
			return EOF;
		}
	}

	public int Peek() {
		int curPos = getPos();
		int ch = Read();
		setPos(curPos);
		return ch;
	}

	public String GetString(int beg, int end) {
	    int len = end - beg;
	    char[] buf = new char[len];
	    int oldPos = getPos();
	    setPos(beg);
	    for (int i = 0; i < len; ++i) buf[i] = (char) Read();
	    setPos(oldPos);
	    return new String(buf);
	}

	public int getPos() {
		return bufPos + bufStart;
	}

	public void setPos(int value) {
		if (value >= fileLen && stream != null) {
			// Wanted position is after buffer and the stream
			// is not seek-able e.g. network or console,
			// thus we have to read the stream manually till
			// the wanted position is in sight.
			while (value >= fileLen && ReadNextStreamChunk() > 0);
		}

		if (value < 0 || value > fileLen) {
			throw new FatalError("buffer out of bounds access, position: " + value);
		}

		if (value >= bufStart && value < bufStart + bufLen) { // already in buffer
			bufPos = value - bufStart;
		} else if (file != null) { // must be swapped in
			try {
				file.seek(value);
				bufLen = file.read(buf);
				bufStart = value; bufPos = 0;
			} catch(IOException e) {
				throw new FatalError(e.getMessage());
			}
		} else {
			// set the position to the end of the file, Pos will return fileLen.
			bufPos = fileLen - bufStart;
		}
	}
	
	// Read the next chunk of bytes from the stream, increases the buffer
	// if needed and updates the fields fileLen and bufLen.
	// Returns the number of bytes read.
	private int ReadNextStreamChunk() {
		int free = buf.length - bufLen;
		if (free == 0) {
			// in the case of a growing input stream
			// we can neither seek in the stream, nor can we
			// foresee the maximum length, thus we must adapt
			// the buffer size on demand.
			byte[] newBuf = new byte[bufLen * 2];
			System.arraycopy(buf, 0, newBuf, 0, bufLen);
			buf = newBuf;
			free = bufLen;
		}
		
		int read;
		try { read = stream.read(buf, bufLen, free); }
		catch (IOException ioex) { throw new FatalError(ioex.getMessage()); }
		
		if (read > 0) {
			fileLen = bufLen = (bufLen + read);
			return read;
		}
		// end of stream reached
		return 0;
	}
}

//-----------------------------------------------------------------------------------
// UTF8Buffer
//-----------------------------------------------------------------------------------
class UTF8Buffer extends Buffer {
	UTF8Buffer(Buffer b) { super(b); }

	public int Read() {
		int ch;
		do {
			ch = super.Read();
			// until we find a uft8 start (0xxxxxxx or 11xxxxxx)
		} while ((ch >= 128) && ((ch & 0xC0) != 0xC0) && (ch != EOF));
		if (ch < 128 || ch == EOF) {
			// nothing to do, first 127 chars are the same in ascii and utf8
			// 0xxxxxxx or end of file character
		} else if ((ch & 0xF0) == 0xF0) {
			// 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
			int c1 = ch & 0x07; ch = super.Read();
			int c2 = ch & 0x3F; ch = super.Read();
			int c3 = ch & 0x3F; ch = super.Read();
			int c4 = ch & 0x3F;
			ch = (((((c1 << 6) | c2) << 6) | c3) << 6) | c4;
		} else if ((ch & 0xE0) == 0xE0) {
			// 1110xxxx 10xxxxxx 10xxxxxx
			int c1 = ch & 0x0F; ch = super.Read();
			int c2 = ch & 0x3F; ch = super.Read();
			int c3 = ch & 0x3F;
			ch = (((c1 << 6) | c2) << 6) | c3;
		} else if ((ch & 0xC0) == 0xC0) {
			// 110xxxxx 10xxxxxx
			int c1 = ch & 0x1F; ch = super.Read();
			int c2 = ch & 0x3F;
			ch = (c1 << 6) | c2;
		}
		return ch;
	}
}

//-----------------------------------------------------------------------------------
// StartStates  -- maps charactes to start states of tokens
//-----------------------------------------------------------------------------------
class StartStates {
	class Elem {
		int key, val;
		Elem next;
		Elem(int key, int val) { this.key = key; this.val = val; }
	}

	Elem[] tab = new Elem[128];

	void set(int key, int val) {
		Elem e = new Elem(key, val);
		int k = key % 128;
		e.next = tab[k]; tab[k] = e;
	}

	int state(int key) {
		Elem e = tab[key % 128];
		while (e != null && e.key != key) e = e.next;
		return e == null ? 0: e.val;
	}
}

//-----------------------------------------------------------------------------------
// Scanner
//-----------------------------------------------------------------------------------
public class Scanner {
	static final char EOL = '\n';
	static final int  eofSym = 0;
	static final int maxT = 46;
	static final int noSym = 46;


	public Buffer buffer; // scanner buffer

	Token t;           // current token
	int ch;            // current input character
	int pos;           // byte position of current character
	int col;           // column number of current character
	int line;          // line number of current character
	int oldEols;       // EOLs that appeared in a comment;
	StartStates start; // maps initial token character to start state

	Token tokens;      // list of tokens already peeked (first token is a dummy)
	Token pt;          // current peek token
	
	char[] tokenText = new char[16]; // token text used in NextToken(), dynamically enlarged
	
	public Scanner (String fileName) {
		buffer = new Buffer(fileName);
		Init();
	}
	
	public Scanner(InputStream s) {
		buffer = new Buffer(s);
		Init();
	}
	
	void Init () {
		pos = -1; line = 1; col = 0;
		oldEols = 0;
		NextCh();
		if (ch == 0xEF) { // check optional byte order mark for UTF-8
			NextCh(); int ch1 = ch;
			NextCh(); int ch2 = ch;
			if (ch1 != 0xBB || ch2 != 0xBF) {
				throw new FatalError("Illegal byte order mark at start of file");
			}
			buffer = new UTF8Buffer(buffer); col = 0;
			NextCh();
		}
		start = new StartStates();
		for (int i = 36; i <= 36; ++i) start.set(i, 1);
		for (int i = 65; i <= 90; ++i) start.set(i, 1);
		for (int i = 95; i <= 95; ++i) start.set(i, 1);
		for (int i = 97; i <= 97; ++i) start.set(i, 1);
		for (int i = 100; i <= 122; ++i) start.set(i, 1);
		for (int i = 48; i <= 48; ++i) start.set(i, 40);
		for (int i = 49; i <= 57; ++i) start.set(i, 41);
		start.set(40, 2); 
		start.set(41, 3); 
		start.set(123, 4); 
		start.set(125, 5); 
		start.set(46, 42); 
		start.set(61, 6); 
		start.set(39, 23); 
		start.set(34, 32); 
		start.set(44, 48); 
		start.set(59, 49); 
		start.set(98, 61); 
		start.set(99, 62); 
		start.set(124, 52); 
		start.set(38, 54); 
		start.set(43, 56); 
		start.set(45, 57); 
		start.set(42, 58); 
		start.set(47, 59); 
		start.set(37, 60); 
		start.set(Buffer.EOF, -1);

		pt = tokens = new Token();  // first token is a dummy
	}
	
	void NextCh() {
		if (oldEols > 0) { ch = EOL; oldEols--; }
		else {
			pos = buffer.getPos();
			ch = buffer.Read(); col++;
			// replace isolated '\r' by '\n' in order to make
			// eol handling uniform across Windows, Unix and Mac
			if (ch == '\r' && buffer.Peek() != '\n') ch = EOL;
			if (ch == EOL) { line++; col = 0; }
		}

	}
	

	boolean Comment0() {
		int level = 1, pos0 = pos, line0 = line, col0 = col;
		NextCh();
		if (ch == '/') {
			NextCh();
			for(;;) {
				if (ch == 13) {
					NextCh();
					if (ch == 10) {
						level--;
						if (level == 0) { oldEols = line - line0; NextCh(); return true; }
						NextCh();
					}
				} else if (ch == Buffer.EOF) return false;
				else NextCh();
			}
		} else {
			buffer.setPos(pos0); NextCh(); line = line0; col = col0;
		}
		return false;
	}

	boolean Comment1() {
		int level = 1, pos0 = pos, line0 = line, col0 = col;
		NextCh();
		if (ch == '*') {
			NextCh();
			for(;;) {
				if (ch == '*') {
					NextCh();
					if (ch == '/') {
						level--;
						if (level == 0) { oldEols = line - line0; NextCh(); return true; }
						NextCh();
					}
				} else if (ch == '/') {
					NextCh();
					if (ch == '*') {
						level++; NextCh();
					}
				} else if (ch == Buffer.EOF) return false;
				else NextCh();
			}
		} else {
			buffer.setPos(pos0); NextCh(); line = line0; col = col0;
		}
		return false;
	}

	
	void CheckLiteral() {
		String lit = t.val;
		if (lit.compareTo("static") == 0) t.kind = 12;
		else if (lit.compareTo("final") == 0) t.kind = 13;
		else if (lit.compareTo("class") == 0) t.kind = 14;
		else if (lit.compareTo("extends") == 0) t.kind = 15;
		else if (lit.compareTo("implements") == 0) t.kind = 16;
		else if (lit.compareTo("interface") == 0) t.kind = 17;
		else if (lit.compareTo("public") == 0) t.kind = 18;
		else if (lit.compareTo("boolean") == 0) t.kind = 19;
		else if (lit.compareTo("int") == 0) t.kind = 20;
		else if (lit.compareTo("float") == 0) t.kind = 21;
		else if (lit.compareTo("void") == 0) t.kind = 22;
		else if (lit.compareTo("String") == 0) t.kind = 23;
		else if (lit.compareTo("Vector") == 0) t.kind = 24;
		else if (lit.compareTo("if") == 0) t.kind = 27;
		else if (lit.compareTo("else") == 0) t.kind = 28;
		else if (lit.compareTo("while") == 0) t.kind = 29;
		else if (lit.compareTo("return") == 0) t.kind = 30;
		else if (lit.compareTo("new") == 0) t.kind = 33;
		else if (lit.compareTo("this") == 0) t.kind = 34;
		else if (lit.compareTo("super") == 0) t.kind = 35;
		else if (lit.compareTo("true") == 0) t.kind = 36;
		else if (lit.compareTo("false") == 0) t.kind = 37;
		else if (lit.compareTo("null") == 0) t.kind = 38;
	}

	Token NextToken() {
		while(ch == ' ' ||
			ch >= 9 && ch <= 10 || ch == 13
		) NextCh();
		if (ch == '/' && Comment0() ||ch == '/' && Comment1()) return NextToken();
		t = new Token();
		t.pos = pos; t.col = col; t.line = line; 
		int state = start.state(ch);
		char[] tval = tokenText; // local variables are more efficient
		int tlen = 0;
		tval[tlen++] = (char)ch; NextCh();
		
		boolean done = false;
		while (!done) {
			if (tlen >= tval.length) {
				char[] newBuf = new char[2 * tval.length];
				System.arraycopy(tval, 0, newBuf, 0, tval.length);
				tokenText = tval = newBuf;
			}
			switch (state) {
				case -1: { t.kind = eofSym; done = true; break; } // NextCh already done 
				case 0: { t.kind = noSym; done = true; break; }   // NextCh already done
				case 1:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 2:
					{t.kind = 2; done = true; break;}
				case 3:
					{t.kind = 3; done = true; break;}
				case 4:
					{t.kind = 4; done = true; break;}
				case 5:
					{t.kind = 5; done = true; break;}
				case 6:
					{t.kind = 7; done = true; break;}
				case 7:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 8; break;}
					else {t.kind = noSym; done = true; break;}
				case 8:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 8; break;}
					else if (ch == 'L' || ch == 'l') {tval[tlen++] = (char)ch; NextCh(); state = 9; break;}
					else {t.kind = 8; done = true; break;}
				case 9:
					{t.kind = 8; done = true; break;}
				case 10:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 10; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else if (ch == 'E' || ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 11; break;}
					else {t.kind = 9; done = true; break;}
				case 11:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 13; break;}
					else if (ch == '+' || ch == '-') {tval[tlen++] = (char)ch; NextCh(); state = 12; break;}
					else {t.kind = noSym; done = true; break;}
				case 12:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 13; break;}
					else {t.kind = noSym; done = true; break;}
				case 13:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 13; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = 9; done = true; break;}
				case 14:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 14; break;}
					else if (ch == '.') {tval[tlen++] = (char)ch; NextCh(); state = 15; break;}
					else if (ch == 'E' || ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 19; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = noSym; done = true; break;}
				case 15:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 15; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else if (ch == 'E' || ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 16; break;}
					else {t.kind = 9; done = true; break;}
				case 16:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 18; break;}
					else if (ch == '+' || ch == '-') {tval[tlen++] = (char)ch; NextCh(); state = 17; break;}
					else {t.kind = noSym; done = true; break;}
				case 17:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 18; break;}
					else {t.kind = noSym; done = true; break;}
				case 18:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 18; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = 9; done = true; break;}
				case 19:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 21; break;}
					else if (ch == '+' || ch == '-') {tval[tlen++] = (char)ch; NextCh(); state = 20; break;}
					else {t.kind = noSym; done = true; break;}
				case 20:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 21; break;}
					else {t.kind = noSym; done = true; break;}
				case 21:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 21; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = 9; done = true; break;}
				case 22:
					{t.kind = 9; done = true; break;}
				case 23:
					if (ch <= 9 || ch >= 11 && ch <= 12 || ch >= 14 && ch <= '&' || ch >= '(' && ch <= '[' || ch >= ']' && ch <= 65535) {tval[tlen++] = (char)ch; NextCh(); state = 24; break;}
					else if (ch == 92) {tval[tlen++] = (char)ch; NextCh(); state = 25; break;}
					else {t.kind = noSym; done = true; break;}
				case 24:
					if (ch == 39) {tval[tlen++] = (char)ch; NextCh(); state = 31; break;}
					else {t.kind = noSym; done = true; break;}
				case 25:
					if (ch >= '0' && ch <= '3') {tval[tlen++] = (char)ch; NextCh(); state = 43; break;}
					else if (ch >= '4' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 30; break;}
					else if (ch == '"' || ch == 39 || ch == 92 || ch == 'b' || ch == 'f' || ch == 'n' || ch == 'r' || ch == 't') {tval[tlen++] = (char)ch; NextCh(); state = 24; break;}
					else if (ch == 'u') {tval[tlen++] = (char)ch; NextCh(); state = 26; break;}
					else {t.kind = noSym; done = true; break;}
				case 26:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 27; break;}
					else if (ch == 'u') {tval[tlen++] = (char)ch; NextCh(); state = 26; break;}
					else {t.kind = noSym; done = true; break;}
				case 27:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 28; break;}
					else {t.kind = noSym; done = true; break;}
				case 28:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 29; break;}
					else {t.kind = noSym; done = true; break;}
				case 29:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 24; break;}
					else {t.kind = noSym; done = true; break;}
				case 30:
					if (ch >= '0' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 24; break;}
					else if (ch == 39) {tval[tlen++] = (char)ch; NextCh(); state = 31; break;}
					else {t.kind = noSym; done = true; break;}
				case 31:
					{t.kind = 10; done = true; break;}
				case 32:
					if (ch <= 9 || ch >= 11 && ch <= 12 || ch >= 14 && ch <= '!' || ch >= '#' && ch <= '[' || ch >= ']' && ch <= 65535) {tval[tlen++] = (char)ch; NextCh(); state = 32; break;}
					else if (ch == '"') {tval[tlen++] = (char)ch; NextCh(); state = 39; break;}
					else if (ch == 92) {tval[tlen++] = (char)ch; NextCh(); state = 33; break;}
					else {t.kind = noSym; done = true; break;}
				case 33:
					if (ch >= '0' && ch <= '3') {tval[tlen++] = (char)ch; NextCh(); state = 45; break;}
					else if (ch >= '4' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 38; break;}
					else if (ch == '"' || ch == 39 || ch == 92 || ch == 'b' || ch == 'f' || ch == 'n' || ch == 'r' || ch == 't') {tval[tlen++] = (char)ch; NextCh(); state = 32; break;}
					else if (ch == 'u') {tval[tlen++] = (char)ch; NextCh(); state = 34; break;}
					else {t.kind = noSym; done = true; break;}
				case 34:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 35; break;}
					else if (ch == 'u') {tval[tlen++] = (char)ch; NextCh(); state = 34; break;}
					else {t.kind = noSym; done = true; break;}
				case 35:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 36; break;}
					else {t.kind = noSym; done = true; break;}
				case 36:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 37; break;}
					else {t.kind = noSym; done = true; break;}
				case 37:
					if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F' || ch >= 'a' && ch <= 'f') {tval[tlen++] = (char)ch; NextCh(); state = 32; break;}
					else {t.kind = noSym; done = true; break;}
				case 38:
					if (ch <= 9 || ch >= 11 && ch <= 12 || ch >= 14 && ch <= '!' || ch >= '#' && ch <= '[' || ch >= ']' && ch <= 65535) {tval[tlen++] = (char)ch; NextCh(); state = 32; break;}
					else if (ch == '"') {tval[tlen++] = (char)ch; NextCh(); state = 39; break;}
					else if (ch == 92) {tval[tlen++] = (char)ch; NextCh(); state = 33; break;}
					else {t.kind = noSym; done = true; break;}
				case 39:
					{t.kind = 11; done = true; break;}
				case 40:
					if (ch >= '0' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 47; break;}
					else if (ch >= '8' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 14; break;}
					else if (ch == 'L' || ch == 'l') {tval[tlen++] = (char)ch; NextCh(); state = 9; break;}
					else if (ch == 'X' || ch == 'x') {tval[tlen++] = (char)ch; NextCh(); state = 7; break;}
					else if (ch == '.') {tval[tlen++] = (char)ch; NextCh(); state = 15; break;}
					else if (ch == 'E' || ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 19; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = 8; done = true; break;}
				case 41:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 41; break;}
					else if (ch == 'L' || ch == 'l') {tval[tlen++] = (char)ch; NextCh(); state = 9; break;}
					else if (ch == '.') {tval[tlen++] = (char)ch; NextCh(); state = 15; break;}
					else if (ch == 'E' || ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 19; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = 8; done = true; break;}
				case 42:
					if (ch >= '0' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 10; break;}
					else {t.kind = 6; done = true; break;}
				case 43:
					if (ch >= '0' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 44; break;}
					else if (ch == 39) {tval[tlen++] = (char)ch; NextCh(); state = 31; break;}
					else {t.kind = noSym; done = true; break;}
				case 44:
					if (ch >= '0' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 24; break;}
					else if (ch == 39) {tval[tlen++] = (char)ch; NextCh(); state = 31; break;}
					else {t.kind = noSym; done = true; break;}
				case 45:
					if (ch <= 9 || ch >= 11 && ch <= 12 || ch >= 14 && ch <= '!' || ch >= '#' && ch <= '/' || ch >= '8' && ch <= '[' || ch >= ']' && ch <= 65535) {tval[tlen++] = (char)ch; NextCh(); state = 32; break;}
					else if (ch >= '0' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 46; break;}
					else if (ch == '"') {tval[tlen++] = (char)ch; NextCh(); state = 39; break;}
					else if (ch == 92) {tval[tlen++] = (char)ch; NextCh(); state = 33; break;}
					else {t.kind = noSym; done = true; break;}
				case 46:
					if (ch <= 9 || ch >= 11 && ch <= 12 || ch >= 14 && ch <= '!' || ch >= '#' && ch <= '[' || ch >= ']' && ch <= 65535) {tval[tlen++] = (char)ch; NextCh(); state = 32; break;}
					else if (ch == '"') {tval[tlen++] = (char)ch; NextCh(); state = 39; break;}
					else if (ch == 92) {tval[tlen++] = (char)ch; NextCh(); state = 33; break;}
					else {t.kind = noSym; done = true; break;}
				case 47:
					if (ch >= '0' && ch <= '7') {tval[tlen++] = (char)ch; NextCh(); state = 47; break;}
					else if (ch >= '8' && ch <= '9') {tval[tlen++] = (char)ch; NextCh(); state = 14; break;}
					else if (ch == 'L' || ch == 'l') {tval[tlen++] = (char)ch; NextCh(); state = 9; break;}
					else if (ch == '.') {tval[tlen++] = (char)ch; NextCh(); state = 15; break;}
					else if (ch == 'E' || ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 19; break;}
					else if (ch == 'D' || ch == 'F' || ch == 'd' || ch == 'f') {tval[tlen++] = (char)ch; NextCh(); state = 22; break;}
					else {t.kind = 8; done = true; break;}
				case 48:
					{t.kind = 25; done = true; break;}
				case 49:
					{t.kind = 26; done = true; break;}
				case 50:
					{t.kind = 31; done = true; break;}
				case 51:
					{t.kind = 32; done = true; break;}
				case 52:
					if (ch == '|') {tval[tlen++] = (char)ch; NextCh(); state = 53; break;}
					else {t.kind = noSym; done = true; break;}
				case 53:
					{t.kind = 39; done = true; break;}
				case 54:
					if (ch == '&') {tval[tlen++] = (char)ch; NextCh(); state = 55; break;}
					else {t.kind = noSym; done = true; break;}
				case 55:
					{t.kind = 40; done = true; break;}
				case 56:
					{t.kind = 41; done = true; break;}
				case 57:
					{t.kind = 42; done = true; break;}
				case 58:
					{t.kind = 43; done = true; break;}
				case 59:
					{t.kind = 44; done = true; break;}
				case 60:
					{t.kind = 45; done = true; break;}
				case 61:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'q' || ch >= 's' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'r') {tval[tlen++] = (char)ch; NextCh(); state = 63; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 62:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'n' || ch >= 'p' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'o') {tval[tlen++] = (char)ch; NextCh(); state = 64; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 63:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'd' || ch >= 'f' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 65; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 64:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'm' || ch >= 'o' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'n') {tval[tlen++] = (char)ch; NextCh(); state = 66; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 65:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'b' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'a') {tval[tlen++] = (char)ch; NextCh(); state = 67; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 66:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 's' || ch >= 'u' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 't') {tval[tlen++] = (char)ch; NextCh(); state = 68; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 67:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'j' || ch >= 'l' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'k') {tval[tlen++] = (char)ch; NextCh(); state = 69; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 68:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'h' || ch >= 'j' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'i') {tval[tlen++] = (char)ch; NextCh(); state = 70; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 69:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == ';') {tval[tlen++] = (char)ch; NextCh(); state = 50; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 70:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'm' || ch >= 'o' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'n') {tval[tlen++] = (char)ch; NextCh(); state = 71; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 71:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 't' || ch >= 'v' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'u') {tval[tlen++] = (char)ch; NextCh(); state = 72; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 72:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'd' || ch >= 'f' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == 'e') {tval[tlen++] = (char)ch; NextCh(); state = 73; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}
				case 73:
					if (ch == '$' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch >= 'a' && ch <= 'z') {tval[tlen++] = (char)ch; NextCh(); state = 1; break;}
					else if (ch == ';') {tval[tlen++] = (char)ch; NextCh(); state = 51; break;}
					else {t.kind = 1; t.val = new String(tval, 0, tlen); CheckLiteral(); return t;}

			}
		}
		t.val = new String(tval, 0, tlen);
		return t;
	}
	
	// get the next token (possibly a token already seen during peeking)
	public Token Scan () {
		if (tokens.next == null) {
			return NextToken();
		} else {
			pt = tokens = tokens.next;
			return tokens;
		}
	}

	// get the next token, ignore pragmas
	public Token Peek () {
		if (pt.next == null) {
			do {
				pt = pt.next = NextToken();
			} while (pt.kind > maxT); // skip pragmas
		} else {
			do {
				pt = pt.next;
			} while (pt.kind > maxT);
		}
		return pt;
	}

	// make sure that peeking starts at current scan position
	public void ResetPeek () { pt = tokens; }

} // end Scanner

