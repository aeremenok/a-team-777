package ru.spb.etu.client.ui.edit.entities.traversal;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;
import ru.spb.etu.client.ui.edit.entities.edit.MasterpieceEditPanel;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * панель для правки атрибутов произведения
 * 
 * @author eav
 */
public class LightTraversalPanel
    extends TraversalPanel
    implements
        AsyncCallback
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
        MasterPiece masterPiece = getMasterpiece();
        getCurrentEntity().applyToMasterPiece( masterPiece );
        async.saveOrUpdate( masterPiece, this );
    }

    private MasterPiece getMasterpiece()
    {
        return (MasterPiece) host.getEntityForm().getEntityWrapper();
    }

    public void onClick(
        Widget arg0 )
    {
        super.onClick( arg0 );
        // кнопки здесь не нужны
        remove( buttonPanel );
    }

    public void applyReference()
    {
        EntityWrapper masterpieceReference = entityEditPanel.getMasterpieceReference( getMasterpiece() );
        if ( masterpieceReference != null )
        {
            listBox.clear();
            listBox.addItem( masterpieceReference.getTitle().toString() );
            listBox.removeStyleName( "error" );
        }
        else
            listBox.addStyleName( "error" );
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

    public void onFailure(
        Throwable arg0 )
    {
        Window.alert( arg0.toString() );
    }

    public void onSuccess(
        Object arg0 )
    {
        Integer integer = (Integer) arg0;
        if ( integer.intValue() > 0 )
            getMasterpiece().setId( integer );
        listBox.removeStyleName( "error" );
    }
}
