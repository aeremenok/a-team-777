package ru.spb.hmi.client.docs;

import ru.spb.hmi.client.DOCService;
import ru.spb.hmi.client.DOCServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * класс сборки подполей
 * 
 * @author eav
 */
public class XPathPanel
    extends VerticalPanel
{
    private static DOCServiceAsync _service = DOCService.Util.getInstance();
    final static String            DELIM    = ":";
    String                         type;
    String                         title;
    FocusWidget                    _widget  = null;
    XPathPanel                     parent   = null;

    /**
     * содержит конечный виджет
     * 
     * @param parent
     * @param type
     * @param title
     * @param widget
     */
    public XPathPanel(
        XPathPanel parent,
        String type,
        String title,
        FocusWidget widget )
    {
        super();
        this.type = type;
        this.title = title;
        _widget = widget;
        this.parent = parent;
        add( widget );

        _service.getProperty( getParentType(), new AsyncCallback()
        {
            public void onFailure(
                Throwable caught )
            {
                caught.printStackTrace();
            }

            public void onSuccess(
                Object result )
            {
                setValue( _widget, result );
            }

        } );

        listenChanges( _widget );
    }

    /**
     * отправляет текст на сервер
     * 
     * @author eav
     */
    class EchoingListener
        implements
            ChangeListener
    {
        private String _text;

        public EchoingListener(
            String text )
        {
            _text = text;
        }

        public void onChange(
            Widget sender )
        {
            _service.setProperty( getParentType(), _text, new NotifyCallBack( "property set" ) );

        }
    };

    /**
     * следит за изменениями в зав-ти от типа виджета
     * 
     * @param widget
     */
    private void listenChanges(
        FocusWidget widget )
    {
        if ( widget instanceof TextBoxBase )
        {
            final TextBoxBase textBoxBase = (TextBoxBase) widget;
            textBoxBase.addChangeListener( new EchoingListener( textBoxBase.getText() ) );
        }

    }

    public XPathPanel(
        XPathPanel parent,
        String type,
        String title )
    {
        this( type, title );
        this.parent = parent;
    }

    /**
     * получает путь к себе от корня
     * 
     * @return
     */
    protected String getParentType()
    {
        if ( parent != null )
        {
            return parent.getParentType() + DELIM + type;
        }
        return type;
    }

    private static void setValue(
        Widget widget,
        Object result )
    {
        if ( widget instanceof TextBoxBase )
        {
            TextBoxBase textBoxBase = (TextBoxBase) widget;
            textBoxBase.setText( (String) result );
        }
    }

    public XPathPanel(
        String type,
        String title )
    {
        super();
        this.type = type;
        this.title = title;
        add( new Label( title ) );
    }
}
