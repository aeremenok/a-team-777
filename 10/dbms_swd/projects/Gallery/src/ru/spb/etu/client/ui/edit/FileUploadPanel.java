package ru.spb.etu.client.ui.edit;

import ru.spb.etu.client.ui.view.forms.EntityForm;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadPanel
    extends FormPanel
    implements
        ClickListener,
        FormHandler
{
    private FileUpload fileUpload;
    private EntityForm entityForm;

    public FileUploadPanel(
        EntityForm entityForm )
    {
        setEntityForm( entityForm );
        // связка с сервлетом
        setMethod( METHOD_POST );
        setEncoding( ENCODING_MULTIPART );
        setAction( "/Gallery/FileUploadServlet" );

        // содержимое
        HorizontalPanel verticalPanel = new HorizontalPanel();
        setWidget( verticalPanel );

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
        String results = arg0.getResults();
        results =
                  results.replaceAll( "<PRE>", "" ).replaceAll( "<pre>", "" ).replaceAll( "</PRE>", "" )
                         .replaceAll( "</pre>", "" );
        // Window.alert( results );
        entityForm.setUrl( results );
    }

    public void setEntityForm(
        EntityForm entityForm )
    {
        this.entityForm = entityForm;
    }
}
