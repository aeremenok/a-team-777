package ru.spb.etu.server;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.MasterPiece;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImageServiceImpl
    extends RemoteServiceServlet
    implements
        ImageService
{
    @Override
    public ArrayList getArtists()
    {
        ArrayList artists = new ArrayList();

        Artist artist = new Artist();
        artist.setName( "artist1" );
        artist.setImageUrl( "images/03.jpg" );
        artists.add( artist );

        Artist artist1 = new Artist();
        artist1.setName( "artist2" );
        artist1.setImageUrl( "images/04.jpg" );
        artists.add( artist1 );

        return artists;
    }

    @Override
    public ArrayList getMasterPieces(
        Artist artist )
    {
        ArrayList arrayList = new ArrayList();

        MasterPiece masterPiece = new MasterPiece();
        masterPiece.setTitle( "01" );
        masterPiece.setImageUrl( "images/01.jpg" );
        arrayList.add( masterPiece );

        MasterPiece masterPiece1 = new MasterPiece();
        masterPiece1.setTitle( "02" );
        masterPiece1.setImageUrl( "images/02.jpg" );
        arrayList.add( masterPiece1 );

        return arrayList;
    }
}
