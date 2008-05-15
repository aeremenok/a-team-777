package ru.spb.etu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Compiler
{
    /**
     * @param args
     */
    public static void main(
        String[] args )
    {
        try
        {
            File file = new File( args[0] );
            FileInputStream fileInputStream = new FileInputStream( file );
            Scanner scanner = new Scanner( fileInputStream );
            System.out.println( "scanning" );
            scanner.Scan();

            Parser parser = new MyParser( scanner );
            System.out.println( "parsing" );
            parser.Parse();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }
}
