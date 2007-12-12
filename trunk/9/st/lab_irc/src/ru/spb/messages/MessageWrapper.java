package ru.spb.messages;

/**
 * обертка сообщения, позволяющая обращаться к отдельным полям
 * {@link http://tools.ietf.org/html/rfc2812}
 * 
 * @author eav
 */
public class MessageWrapper
{

    /**
     * The prefix is used by servers to indicate the true origin of the message.
     * If the prefix is missing from the message, it is assumed to have
     * originated from the connection from which it was received from. Clients
     * SHOULD NOT use a prefix when sending a message; if they use one, the only
     * valid prefix is the registered nickname associated with the client.
     */
    private String             prefix;
    /**
     * The presence of a prefix is indicated with a single leading ASCII colon
     * character (':', 0x3b), which MUST be the first character of the message
     * itself. There MUST be NO gap (whitespace) between the colon and the
     * prefix.
     */
    public static final char   PREFIX_STARTER = ':';
    /**
     * The command MUST either be a valid IRC command or a three (3) digit
     * number represented in ASCII text
     */
    private String             command;
    private final String       params[]       = new String[MAX_PARAMS];
    public static final int    MAX_PARAMS     = 15;

    private byte               nospcrlfcl;
    private String             middle;
    private String             trailing;

    /**
     * todo в каком формате это должно быть?
     */
    public static final String SPACE          = "%x20";
    public static final String CRLF           = "%x0D %x0A";

    public MessageWrapper(
        Message message )
    {
        wrapMessage( message );
    }

    /**
     * преобразовать поля в непрерывное сообщение
     * 
     * @return сообщение, годное к отправке
     */
    public Message unwrapMessage()
    {
        Message res = new Message();
        res.concat( prefix );
        // todo
        return res;
    }

    /**
     * разобрать сообщение по полям
     * 
     * @param message исходное сообщение
     */
    public void wrapMessage(
        Message message )
    {
        // todo
    }
}
