package ru.spb.etu.server.model;

import java.util.List;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import ru.spb.etu.server.model.auto._DbMuseum;

public class DbMuseum
    extends _DbMuseum
{
    public Integer getId()
    {
        return DataObjectUtils.intPKForObject( this );
    }

    /**
     * @param context
     * @return все произведения искусства в этом музее
     */
    public List<DbMasterpiece> getMasterpieces(
        DataContext context )
    {
        Expression qualifier = ExpressionFactory.matchExp( DbMasterpiece.MY_MUSEUM_PROPERTY, getId() );
        SelectQuery select = new SelectQuery( DbMasterpiece.class, qualifier );
        return context.performQuery( select );
    }
}
