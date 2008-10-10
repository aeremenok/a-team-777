package talkie.server.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;

public class UserTableModel
    extends AbstractTableModel
{
    String[]                     columnNames = new String[] { "Имя", "Пароль" };
    ArrayList<ArrayList<String>> data        = new ArrayList<ArrayList<String>>();

    public UserTableModel(
        Properties users )
    {
        Iterator<String> iterator = users.stringPropertyNames().iterator();
        while ( iterator.hasNext() )
        {
            String key = iterator.next();
            String value = users.getProperty( key );

            ArrayList<String> row = new ArrayList<String>();
            row.add( key );
            row.add( value );

            data.add( row );
        }
    }

    public int getColumnCount()
    {
        return 2;
    }

    @Override
    public String getColumnName(
        int column )
    {
        return columnNames[column];
    }

    public int getRowCount()
    {
        return data.size();
    }

    public Object getValueAt(
        int rowIndex,
        int columnIndex )
    {
        return data.get( rowIndex ).get( columnIndex );
    }
}
