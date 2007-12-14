package ru.spb.messages;

/**
 * Error replies are found in the range from 400 to 599
 * 
 * @author eav
 */
public interface Errors
{
    public static final int ERR_NOSUCHNICK       = 401;
    public static final int ERR_NOSUCHSERVER     = 402;
    public static final int ERR_NOSUCHCHANNEL    = 403;
    public static final int ERR_CANNOTSENDTOCHAN = 404;
    public static final int ERR_TOOMANYCHANNELS  = 405;
    public static final int ERR_WASNOSUCHNICK    = 406;
    public static final int ERR_TOOMANYTARGETS   = 407;
    public static final int ERR_NOORIGIN         = 409;
    public static final int ERR_NORECIPIENT      = 411;
    public static final int ERR_NOTEXTTOSEND     = 412;
    public static final int ERR_NOTOPLEVEL       = 413;
    public static final int ERR_WILDTOPLEVEL     = 414;
    public static final int ERR_UNKNOWNCOMMAND   = 421;
    public static final int ERR_NOMOTD           = 422;
    public static final int ERR_NOADMININFO      = 423;
    public static final int ERR_FILEERROR        = 424;
    public static final int ERR_NONICKNAMEGIVEN  = 431;
    public static final int ERR_ERRONEUSNICKNAME = 432;
    public static final int ERR_NICKNAMEINUSE    = 433;
    public static final int ERR_NICKCOLLISION    = 436;
    public static final int ERR_USERNOTINCHANNEL = 441;
    public static final int ERR_NOTONCHANNEL     = 442;
    public static final int ERR_USERONCHANNEL    = 443;
    public static final int ERR_NOLOGIN          = 444;
    public static final int ERR_SUMMONDISABLED   = 445;
    public static final int ERR_USERSDISABLED    = 446;
    public static final int ERR_NOTREGISTERED    = 451;
    public static final int ERR_NEEDMOREPARAMS   = 461;
    public static final int ERR_ALREADYREGISTRED = 462;
    public static final int ERR_NOPERMFORHOST    = 463;
    public static final int ERR_PASSWDMISMATCH   = 464;
    public static final int ERR_YOUREBANNEDCREEP = 465;
    public static final int ERR_KEYSET           = 467;
    public static final int ERR_CHANNELISFULL    = 471;
    public static final int ERR_UNKNOWNMODE      = 472;
    public static final int ERR_INVITEONLYCHAN   = 473;
    public static final int ERR_BANNEDFROMCHAN   = 474;
    public static final int ERR_BADCHANNELKEY    = 475;
    public static final int ERR_NOPRIVILEGES     = 481;
    public static final int ERR_CHANOPRIVSNEEDED = 482;
    public static final int ERR_CANTKILLSERVER   = 483;
    public static final int ERR_NOOPERHOST       = 491;
    public static final int ERR_UMODEUNKNOWNFLAG = 501;
    public static final int ERR_USERSDONTMATCH   = 502;

    public static final int ERR_UNAVAILRESOURCE  = 437;
    public static final int ERR_RESTRICTED       = 484;
    public static final int ERR_TOOMANYMATCHES   = 402;
}
