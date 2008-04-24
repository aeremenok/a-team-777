package ru.spb.etu.server.stubs;

import java.util.ArrayList;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.server.EntityExtractor;

public class EntityExtractorStub
    implements
        EntityExtractor
{

    private Artist artist;
    private Artist artist1;

    @Override
    public ArrayList getArtists()
    {
        ArrayList artists = new ArrayList();

        artist = new Artist();
        artist.setName( "artist1" );
        artist.setImageUrl( "images/03.JPG" );
        artist.setDescription( "dhfasdghfhasdgvgasdvcbvxzbcvd" + "fashdfjhasjdfh" + "dsajfhjksdah" );
        artists.add( artist );

        artist1 = new Artist();
        artist1.setName( "artist2" );
        artist1.setImageUrl( "images/04.JPG" );
        artist1.setDescription( "dhfasdghfhasdgvgasdvcbvxzbcvd" + "fashdfjhasjdfh" + "dsajfhjksdah" );
        artists.add( artist1 );

        return artists;
    }

    @Override
    public ArrayList getMasterPieces(
        Artist artist )
    {
        ArrayList arrayList = new ArrayList();

        if ( artist.equals( this.artist ) )
        {
            for ( int i = 0; i < 100; i++ )
            {
                MasterPiece masterPiece = new MasterPiece();
                masterPiece.setTitle( "01" );
                masterPiece.setImageUrl( "images/01.JPG" );
                masterPiece.setDescription( "dhfasdghfhasdgvgasdvcbvxzbcvd" + "fashdfjhasjdfh" + "dsajfhjksdah" );
                arrayList.add( masterPiece );
            }
        }
        else if ( artist.equals( this.artist1 ) )
        {
            for ( int i = 0; i < 100; i++ )
            {
                MasterPiece masterPiece1 = new MasterPiece();
                masterPiece1.setTitle( "02" );
                masterPiece1.setImageUrl( "images/02.JPG" );
                masterPiece1.setDescription( "dhfasdghfhasdgvgasdvcbvxzbcvd" + "fashdfjhasjdfh" + "dsajfhjksdah" );
                arrayList.add( masterPiece1 );
            }
        }

        return arrayList;
    }

    @Override
    public ArrayList getMuseums()
    {
        ArrayList arrayList = new ArrayList();

        Museum museum = new Museum();
        museum.setDescription( "sdhgfhasdgfhgasdh" );
        museum.setName( "hermitage" );
        museum.setImageUrl( "images/hermitage.jpg" );
        arrayList.add( museum );

        Museum museum1 = new Museum();
        museum1.setDescription( "sdhgfhasdgfhgasdh" );
        museum1.setName( "russian museum" );
        museum1.setImageUrl( "images/russmus.jpg" );
        arrayList.add( museum1 );

        return arrayList;
    }

    @Override
    public ArrayList getGenres()
    {
        ArrayList arrayList = new ArrayList();

        Genre genre = new Genre();
        genre.setDescription( "dfgasdfhdashgdhgfash" );
        genre.setName( "portrait" );
        genre.setImageUrl( "images/portrait.jpg" );
        arrayList.add( genre );

        Genre genre1 = new Genre();
        genre1.setDescription( "dfgasdfhdashgdhgfash" );
        genre1.setName( "peisage" );
        genre1.setImageUrl( "images/peisage.jpg" );
        arrayList.add( genre1 );

        return arrayList;
    }

    @Override
    public ArrayList getArtistsByGenre(
        Genre genre )
    {
        return getArtists();
    }

    @Override
    public ArrayList getArtistsByMuseum(
        Museum museum )
    {
        return getArtists();
    }

}
