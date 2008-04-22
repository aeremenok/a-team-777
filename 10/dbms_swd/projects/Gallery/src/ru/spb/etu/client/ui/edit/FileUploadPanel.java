package ru.spb.etu.client.ui.edit;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadPanel
    extends FormPanel
    implements
        ClickListener,
        FormHandler
{
    private static FileUploadPanel instance;
    private HTML                   label;
    private FileUpload             fileUpload;

    public static FileUploadPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new FileUploadPanel();
        }
        return instance;
    }

    public FileUploadPanel()
    {
        // связка с сервлетом
        setMethod( METHOD_POST );
        setEncoding( ENCODING_MULTIPART );
        setAction( "/Gallery/FileUploadServlet" );

        // содержимое
        VerticalPanel verticalPanel = new VerticalPanel();
        setWidget( verticalPanel );

        label = new HTML( "Specify File" );
        verticalPanel.add( label );

        fileUpload = new FileUpload();
        fileUpload.setName( "testcaseFile" );
        verticalPanel.add( fileUpload );

        Button button = new Button( "Submit", this );
        verticalPanel.add( button );

        addFormHandler( this );
    }

    public void onClick(
        Widget arg0 )
    {
        submit();
    }

    public void onSubmit(
        FormSubmitEvent arg0 )
    {
        if ( fileUpload.getFilename().equals( "" ) )
        {
            Window.alert( "empty path" );
            arg0.setCancelled( true );
        }
    }

    public void onSubmitComplete(
        FormSubmitCompleteEvent arg0 )
    {
        label.setText( arg0.getResults() );
    }
}
