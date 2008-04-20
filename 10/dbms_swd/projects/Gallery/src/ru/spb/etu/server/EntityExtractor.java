package ru.spb.etu.server;

import java.util.ArrayList;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.Museum;

public interface EntityExtractor
{
    ArrayList getArtists();

    ArrayList getMasterPieces(
        Artist artist );

    ArrayList getMuseums();

    ArrayList getGenres();

    ArrayList getArtistsByGenre(
        Genre genre );

    ArrayList getArtistsByMuseum(
        Museum museum );

}
