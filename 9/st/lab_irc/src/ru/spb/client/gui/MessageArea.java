package ru.spb.client.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.ChatLogPanel;

public class MessageArea
    extends JTextArea
{

    private ChatLogPanel _chatLogPanel;

    public MessageArea(
        ChatLogPanel chatLogPanel )
    {
        super();
        _chatLogPanel = chatLogPanel;
        this.addKeyListener( new KeyListener()
        {
            @Override
            public void keyPressed(
                KeyEvent e )
            {
                if ( e.isControlDown() && (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') )
                {
                    _chatLogPanel.logMessage( User.getCurrentUser(), MessageArea.this.getText() );
                    MessageArea.this.setText( "" );
                    MessageArea.this.setSelectionStart( 0 );
                    MessageArea.this.setSelectionEnd( 0 );
                }
            }

            @Override
            public void keyReleased(
                KeyEvent e )
            {
            }

            @Override
            public void keyTyped(
                KeyEvent e )
            {
            }
        } );
    }
}
