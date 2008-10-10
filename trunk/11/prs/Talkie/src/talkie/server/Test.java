package talkie.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Test
{

    /**
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(
        String[] args )
        throws FileNotFoundException,
            IOException
    {
        Properties users = new Properties();
        users.put( "admin", "admin" );
        users.put( "ssv", "123" );
        users.put( "eav", "123" );
        users.put( "epa", "123" );

        users.store( new FileOutputStream( "users.properties" ), "" );
    }

}
