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
    private IChattable _chattable;

    public MessageArea(
        IChattable chattable )
    {
        super();

        _chattable = chattable;

        this.addKeyListener( new KeyListener()
        {
            @Override
            public void keyPressed(
                KeyEvent e )
            {
                if ( e.isControlDown() && (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') )
                {
                    String message = MessageArea.this.getText();
                    _chattable.say( new PrivateMessage( User.getCurrentUser(), _chattable, message ) );
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
