package ru.spb.etu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
            File file = new File( "" );
            FileInputStream fileInputStream = new FileInputStream( file );
            Scanner scanner = new Scanner( fileInputStream );
            scanner.Scan();

            Parser parser = new Parser( scanner );
            parser.Parse();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }
}
