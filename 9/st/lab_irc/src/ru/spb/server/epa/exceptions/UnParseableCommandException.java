package epa.exceptions;

/**
 * User: Павел
 * Date: 09.12.2007
 * Time: 15:22:24
 */
 public class UnParseableCommandException extends IRCServerException{

        public UnParseableCommandException() {
            this.message = "Can not parse command";
        }

    }
