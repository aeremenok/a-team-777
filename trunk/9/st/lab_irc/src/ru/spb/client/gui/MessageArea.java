package ru.spb.client.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import ru.spb.client.entities.IChattable;
import ru.spb.client.entities.User;
import ru.spb.messages.PrivateMessage;

public class MessageArea
    extends JTextArea
{
    public MessageArea(
        final IChattable chattable )
    {
        super();

        this.addKeyListener( new KeyListener()
        {
            @Override
            public void keyPressed(
                KeyEvent e )
            {
                if ( e.isControlDown() && (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') )
                {
                    String message = MessageArea.this.getText();
                    chattable.say( new PrivateMessage( User.getCurrentUser().getName(), chattable.getName(), message ) );
                    MessageArea.this.setText( "" );
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
