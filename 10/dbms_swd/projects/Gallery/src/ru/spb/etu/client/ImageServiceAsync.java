/**
 * 
 */
package ru.spb.etu.client;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.Museum;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author eav
 * @generated generated asynchronous callback interface to be used on the client side
 *
 */
public interface ImageServiceAsync
{

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void getMasterPieces(
        Artist artist,
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void getArtists(
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void getMuseums(
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void getGenres(
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void getArtistsByGenre(
        Genre genre,
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void getArtistsByMuseum(
        Museum museum,
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void setBaseUrl(
        String url,
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void saveOrUpdate(
        EntityWrapper entityWrapper,
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void remove(
        EntityWrapper entityWrapper,
        AsyncCallback callback );

    /**
     * @param  callback the callback that will be called to receive the return value
     * @generated generated method with asynchronous callback parameter to be used on the client side
     */
    void create(
        String type,
        AsyncCallback callback );

}
