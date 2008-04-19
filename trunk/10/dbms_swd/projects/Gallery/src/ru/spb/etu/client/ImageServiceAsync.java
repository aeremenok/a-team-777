/**
 * 
 */
package ru.spb.etu.client;

import ru.spb.etu.client.serializable.Artist;

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

}
