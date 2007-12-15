package ru.spb.client.gui.logpanels;

import java.awt.Component;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ru.spb.client.entities.IChattable;
import ru.spb.client.gui.ReadOnlyTable;
import ru.spb.messages.PrivateMessage;

/**
 * таблица, куда отображается лог <b>многострочных</b> сообщений
 * 
 * @author eav
 */
public class ChatLogPanel
    extends IRCLogPanel
{
    /**
     * c кем чат todo можно не хранить
     */
    private IChattable _chattable;

    public ChatLogPanel(
        IChattable chattable )
    {
        _chattable = chattable;
        _chattable.addMessageListener( new MessageListener()
        {
            @Override
            public void onMessage(
                PrivateMessage message )
            {
                logMessage( message.getFrom(), message.getMessageString() );
            }
        } );

        _logTableModel = new DefaultTableModel()
        {
            public Class getColumnClass(
                int columnIndex )
            {
                return String.class;
            }
        };
        setColumns();

        _logTable = new ReadOnlyTable( _logTableModel );
        _logTable.setDefaultRenderer( String.class, new MultiLineCellRenderer() );
        _logTable.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
        add( _logTable );
        setViewportView( _logTable );
    }

    @Override
    protected void setColumns()
    {
        _logTableModel.addColumn( "User" );
        _logTableModel.addColumn( "Date" );
        _logTableModel.addColumn( "Message" );
    }

    /**
     * отобразить сообщение от пользователя
     * 
     * @param chattable отправитель(м.б. служебное канала)
     * @param _message его сообщение
     */
    public void logMessage(
        IChattable chattable,
        String message )
    {
        Date current = new Date();
        String name = chattable.getName();
        Object[] rowData = new Object[] { name, current, message };

        // добавляем сообщение в конец
        _logTableModel.addRow( rowData );
        // задаем высоту строки
        StringTokenizer stringTokenizer = new StringTokenizer( message, "\n" );

        int lineCount = stringTokenizer.countTokens();
        int correctRowHeight = _logTable.getRowHeight( _logTable.getRowCount() - 1 ) * lineCount;
        _logTable.setRowHeight( _logTable.getRowCount() - 1, correctRowHeight );
    }

    /**
     * управляет отображением многострочных ячеек
     * 
     * @author eav
     */
    private class MultiLineCellRenderer
        extends JTextArea
        implements
            TableCellRenderer
    {

        public MultiLineCellRenderer()
        {
            setLineWrap( true );
            setWrapStyleWord( true );
            setOpaque( true );
        }

        public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column )
        {
            if ( isSelected )
            {
                setForeground( table.getSelectionForeground() );
                setBackground( table.getSelectionBackground() );
            }
            else
            {
                setForeground( table.getForeground() );
                setBackground( table.getBackground() );
            }
            setFont( table.getFont() );
            if ( hasFocus )
            {
                setBorder( UIManager.getBorder( "Table.focusCellHighlightBorder" ) );
                if ( table.isCellEditable( row, column ) )
                {
                    setForeground( UIManager.getColor( "Table.focusCellForeground" ) );
                    setBackground( UIManager.getColor( "Table.focusCellBackground" ) );
                }
            }
            else
            {
                setBorder( new EmptyBorder( 1, 2, 1, 2 ) );
            }
            setText( (value == null) ? "" : value.toString() );
            return this;
        }
    }

    public IChattable getChattable()
    {
        return _chattable;
    }

}
