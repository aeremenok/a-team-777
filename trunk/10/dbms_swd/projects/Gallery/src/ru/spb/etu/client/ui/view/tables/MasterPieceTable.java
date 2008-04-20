package ru.spb.etu.client.ui.view.tables;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.view.forms.MasterPieceForm;

public class MasterPieceTable
    extends CyclingTable
{

    public MasterPieceTable(
        int width )
    {
        super( width );
    }

    public EntityForm createEntityForm(
        EntityWrapper entityWrapper )
    {
        return new MasterPieceForm( (MasterPiece) entityWrapper );
    }

}
