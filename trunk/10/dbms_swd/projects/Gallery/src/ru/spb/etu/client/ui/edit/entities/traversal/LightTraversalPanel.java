package ru.spb.etu.client.ui.edit.entities.traversal;

import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.Genre;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.serializable.Museum;
import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;
import ru.spb.etu.client.ui.edit.entities.edit.MasterpieceEditPanel;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.ui.Widget;

/**
 * панель для правки атрибутов произведения
 * 
 * @author eav
 */
public class LightTraversalPanel
    extends TraversalPanel
{
    private MasterpieceEditPanel host;

    public LightTraversalPanel(
        MasterpieceEditPanel masterpieceEditPanel,
        EntityEditPanel entityEditPanel )
    {
        super( entityEditPanel );
        host = masterpieceEditPanel;
    }

    public void onChange(
        Widget arg0 )
    {
        // ничего не показываем, только задаем
        MasterPiece masterPiece = (MasterPiece) host.getEntityForm().getEntityWrapper();
        EntityWrapper currentEntity = getCurrentEntity();
        if ( currentEntity instanceof Artist )
        {
            masterPiece.setArtist( (Artist) currentEntity );
        }
        else if ( currentEntity instanceof Genre )
        {
            masterPiece.setGenre( (Genre) currentEntity );
        }
        else if ( currentEntity instanceof Museum )
        {
            masterPiece.setMuseum( (Museum) currentEntity );
        }
    }

    public void onClick(
        Widget arg0 )
    {
        super.onClick( arg0 );
        // кнопки здесь не нужны
        remove( buttonPanel );
    }

    public void updateName(
        MyTextBox name )
    {
    }

    protected void addNewEntity()
    {
    }

    protected void deleteCurrentEntity()
    {
    }

    protected void processNewEntity(
        EntityWrapper entityWrapper )
    {
    }
}
