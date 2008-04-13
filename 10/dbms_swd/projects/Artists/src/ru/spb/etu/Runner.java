package ru.spb.etu;

import org.apache.cayenne.access.DataContext;

public class Runner
{
    /**
     * @param args
     */
    public static void main(
        String[] args )
    {
        DataContext context = DataContext.createDataContext();

        Artist picasso = (Artist) context.newObject( Artist.class );
        picasso.setName( "Pablo Picasso" );
        picasso.setDateOfBirthString( "18811025" );

        Gallery metropolitan = (Gallery) context.newObject( Gallery.class );
        metropolitan.setName( "Metropolitan Museum of Art" );

        Painting girl = (Painting) context.newObject( Painting.class );
        girl.setTitle( "Girl Reading at a Table" );

        Painting stein = (Painting) context.newObject( Painting.class );
        stein.setTitle( "Gertrude Stein" );

        picasso.addToPaintings( girl );
        picasso.addToPaintings( stein );

        girl.setGallery( metropolitan );
        stein.setGallery( metropolitan );

        context.commitChanges();
    }
}
