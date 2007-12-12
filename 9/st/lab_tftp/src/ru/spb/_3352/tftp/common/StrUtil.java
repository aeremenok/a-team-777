package ru.spb._3352.tftp.common;

public class StrUtil
{
    private StrUtil()
    {
        // ���������� ����� ������ ������ ���������
    }

    /**
     * ������ �������������� �����
     * 
     * @param packet �����, �� �������� ����������� ������ �����
     * @param index ������ ����� � �������
     * @param bytes ������ ���� ������
     * @param options �������������� �����
     * @return ��������� ��������� ����� � �������
     * @throws InstantiationException
     */
    public static int readOption(
        TFTPPacket packet,
        int index,
        byte[] bytes,
        TFTPOptions options )
        throws InstantiationException
    {

        // ���� ��� � ����� ������ - ������� ����� ���
        if ( index >= bytes.length )
        {
            return index;
        }

        StringBuffer sb = new StringBuffer();
        int IX_VALUE = readString( index, bytes, sb );
        String option = sb.toString();
        System.out.println( "name of option: " + option );

        // �������� �� ����� �� ������� ���������
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

        // ���������, ���� �� � ��� �� ����� �����
        if ( options == null )
        {
            options = new TFTPOptions( 3 );
        }

        // ��������� ��������� �����
        options.put( option, value );

        return index;
    }

    /**
     * ���������� ������ ������� ���� � ������� ��� � StringBuffer
     * 
     * @param index ��������� �������
     * @param b ������ ����
     * @param sb ������
     * @return ������ ���������� �������� � �������
     */
    public static int readString(
        int index,
        byte[] b,
        StringBuffer sb )
    {
        // ��������� ������ ���� � ������, ������� � ���������� �������.
        // ���������� ��� ������� ����� ���� ��� ����� �������.
        int i;
        for ( i = index; i < b.length; i++ )
        {
            // ������� ���� ����������� ������
            if ( b[i] == 0 )
            {
                break;
            }

            // ��������� ������� � �����
            sb.append( (char) b[i] );
        }

        return ++i;
    }
}
