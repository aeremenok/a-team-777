package Bubbles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Bubbles
        extends JFrame
{

    /**
     * @param args
     */
    final int maxNumberOfRounds = 5;
    final int maxRad = 70;
    final int minRad = 30;
    Round arrayOfRounds[] = new Round[maxNumberOfRounds];

    public static void main( String[] args )
    {
        Bubbles frame = new Bubbles();
        frame.setTitle( "Bubbles" );
        Dimension desktopSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize( (int) ( desktopSize.getWidth() / 2 ), (int) ( desktopSize.getHeight() / 2 ) );
        frame.setVisible( true );
    }

    public void paint( Graphics g )
    {
        Round round = null;
        int i = 0;
        int l = arrayOfRounds.length;

        g.clearRect( 0, 0, getWidth(), getHeight() );

        while ( i < arrayOfRounds.length )
        {
            round = arrayOfRounds[ i ];

            if ( round != null )
            {
                g.drawOval( round.getX() - round.getRadius(), round.getY() - round.getRadius(), round.getRadius(),
                            round.getRadius() );
            }
            i++;
        }
    }

    public Bubbles()
    {
        this.addMouseMotionListener( new MouseAdapter()
        {
            public void mouseMoved( MouseEvent e )
            {
                boolean repaint = false;
                Bubbles bub = Bubbles.this;
                Random rand = new Random( System.currentTimeMillis() );

                for ( int i = 0; i < maxNumberOfRounds; i++ )
                {
                    Round r = arrayOfRounds[ i ];

                    if ( r != null )
                    {
                        if ( Math.pow( r.getX() - e.getX(), 2 ) + Math.pow( r.getY() - e.getY(), 2 )
                                <= Math.pow( r.getRadius(), 2 ) )
                        {
                            repaint = true;
                            int newX = rand.nextInt( Math.abs( bub.getWidth() - 4 * r.getRadius() ) ) + r.getRadius();

                            if ( newX > e.getX() - r.getRadius() )
                            {
                                newX += 2 * r.getRadius();
                            }

                            r.setX( newX );
                            int newY = rand.nextInt( Math.abs( bub.getHeight() - 4 * r.getRadius() ) ) + r.getRadius();

                            if ( newY > e.getY() - r.getRadius() )
                            {
                                newY += 2 * r.getRadius();
                            }

                            r.setY( newY );
                        }
                    }
                }

                if ( repaint )
                {
                    Bubbles.this.repaint();
                }
            }
        } );

        this.addComponentListener( new ComponentAdapter()
        {
            public void componentResized( ComponentEvent e )
            {
                if ( Bubbles.this.getWidth() < 2 * Bubbles.this.maxRad || // If window less then the biggest round
                        Bubbles.this.getHeight() < 2 * Bubbles.this.maxRad ) //then resize
                {
                    Bubbles.this.setSize( max( Bubbles.this.getWidth(), 2 * Bubbles.this.maxRad ),
                                          max( Bubbles.this.getHeight(), 2 * Bubbles.this.maxRad ) );
                }

                Random rand = new Random( System.currentTimeMillis() );
                int numEl = rand.nextInt( maxNumberOfRounds - 1 ) + 1; // Calculate number of round

                for ( int i = 0; i < numEl; i++ )
                {
                    int r = rand.nextInt( maxRad - minRad ) + minRad;
                    arrayOfRounds[ i ] = new Round( rand.nextInt( getWidth() - 2 * r ) + r,
                                                    rand.nextInt( getHeight() - 2 * r ) + r, r );
                    arrayOfRounds[ i ].printInConsol();
                }

                Bubbles.this.repaint();
            }
        } );
    }

    private int max(
            int a,
            int b )
    {
        return a > b ? a : b;
    }

    private class Round
    {
        int x = 0, y = 0, r = 0;

        Round(
                int x,
                int y,
                int r )
        {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }

        public int getRadius()
        {
            return r;
        }

        public void setX( int x )
        {
            this.x = x;
        }

        public void setY( int y )
        {
            this.y = y;
        }

        public void setRadius( int r )
        {
            this.r = r;
        }

        public void printInConsol()
        {
            System.out.println( "(" + x + "," + y + "," + r + ")" );
        }
    }

}


