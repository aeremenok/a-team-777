package ru.spb._3352.tftp.common;

public class StrUtil
{
    private StrUtil()
    {
        // щйгелокъпш щрнцн йкюяяю мекэгъ янгдюбюрэ
    }

    /**
     * времхе днонкмхрекэмшу ножхи
     * 
     * @param packet оюйер, хг йнрнпнцн онхгбндхряъ времхе ножхи
     * @param index хмдейя ножхх б люяяхбе
     * @param bytes люяяхб аюир оюйерю
     * @param options днонкмхрекэмше ножхх
     * @return онкнфемхе якедсчыеи ножхх б люяяхбе
     * @throws InstantiationException
     */
    public static int readOption(
        TFTPPacket packet,
        int index,
        byte[] bytes,
        TFTPOptions options )
        throws InstantiationException
    {

        // еякх сфе б йнмже оюйерю - мхйюйху ножхи мер
        if ( index >= bytes.length )
        {
            return index;
        }

        StringBuffer sb = new StringBuffer();
        int IX_VALUE = readString( index, bytes, sb );
        String option = sb.toString();
        System.out.println( "name of option: " + option );

        // опнбепйю мю бшунд гю цпюмхжш дхюоюгнмю
        if ( IX_VALUE >= bytes.length )
        {
            throw new InstantiationException( "TFTP packet passed to constructor is not a complete "
                                              + packet.getClass().getName() + " packet! Missing value for option: "
                                              + option );
        }

        sb = new StringBuffer();
        index = readString( IX_VALUE, bytes, sb );
        String value = sb.toString();
        System.out.println( "value of option: " + value );

        // опнбепъел, ашкх кх с мюя дн щрнцн ножхх
        if ( options == null )
        {
            options = new TFTPOptions( 3 );
        }

        // днаюбкъел явхрюммсч ножхч
        options.put( option, value );

        return index;
    }

    /**
     * онаюирнбне времхе люяяхбю аюир х оепебнд ецн б StringBuffer
     * 
     * @param index ярюпрнбюъ онгхжхъ
     * @param b люяяхб аюир
     * @param sb ярпнйю
     * @return хмдейя якедсчыецн щкелемрю б люяяхбе
     */
    public static int readString(
        int index,
        byte[] b,
        StringBuffer sb )
    {
        // оепебндхл люяяхб аюир б ярпнйс, мювхмюъ я сйюгюммнцн хмдейяю.
        // опейпюыюел опх мскебнл аюире кхан опх йнмже люяяхбю.
        int i;
        for ( i = index; i < b.length; i++ )
        {
            // мскебни аюир гюйюмвхбюер ярпнйс
            if ( b[i] == 0 )
            {
                break;
            }

            // днаюбкъел яхлбнкш б астеп
            sb.append( (char) b[i] );
        }

        return ++i;
    }
}
