package ru.spb.client.gui.dialogs;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * простейший диалог задания параметров
 * 
 * @author eav
 */
public abstract class ParamsRequest
    extends JDialog
{

    protected JPanel _contentPane  = new JPanel();
    private JButton  _buttonOK     = new JButton( "OK" );
    private JButton  _buttonCancel = new JButton( "Cancel" );

    public ParamsRequest(
        Frame owner,
        String title )
    {
        super( owner, true );
        setTitle( title );

        setContentPane( _contentPane );
        _contentPane.add( _buttonOK );
        _contentPane.add( _buttonCancel );

        getRootPane().setDefaultButton( _buttonOK );

        ActionListener okActionListener = new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                onOK();
            }
        };

        ActionListener cancelActionListener = new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                onCancel();
            }
        };

        _buttonOK.addActionListener( okActionListener );

        _buttonCancel.addActionListener( cancelActionListener );
        // call onCancel() on ESCAPE
        _contentPane.registerKeyboardAction( cancelActionListener, KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ),
                                             JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );

        // call onCancel() when cross is clicked
        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

        addWindowListener( new WindowAdapter()
        {
            public void windowClosing(
                WindowEvent e )
            {
                onCancel();
            }
        } );
    }

    private void onOK()
    {
        readParams();
        dispose();
    }

    protected abstract void readParams();

    private void onCancel()
    {
        dispose();
    }

    public static void showOnCenter(
        Window win )
    {
        win.pack();
        reallyShowOnCenter( win );
    }

    public static final Point     CENTER_OF_SCREEN = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
    public static final Rectangle SCREEN_DIMENSION =
                                                     GraphicsEnvironment.getLocalGraphicsEnvironment()
                                                                        .getMaximumWindowBounds();

    private static void reallyShowOnCenter(
        Window win )
    {
        int x = (CENTER_OF_SCREEN.x - win.getSize().width / 2);
        int y = (CENTER_OF_SCREEN.y - win.getSize().height / 2);
        win.setLocation( x, y );
        win.setVisible( true );
    }

}
