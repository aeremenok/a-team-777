import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA. User: ssv Date: 29.10.2007 Time: 12:19:01 To change this template use File | Settings |
 * File Templates.
 */
public class TestApplet
        extends Applet
{
    /**
     * ������ ��������� �����
     */
    private Random _random = null;

    /**
     * ������� ������� ������� (��� �������� onresize)
     */
    private Dimension _dimension = null;

    /**
     * ������ �����
     */
    private int _diameter = 40;

    /**
     * ������ ������ ��� ���������
     */
    private ArrayList<Circle> _circleList = null;

    public void start()
    {
        _dimension = getSize();

        addMouseMotionListener( new MouseMotionListener()
        {
            public void mouseDragged( MouseEvent e )
            {
                // ������ �� ������
            }

            public void mouseMoved( MouseEvent e )
            {
                // ����� ��������� �������
                Point cursor = new Point( e.getX(), e.getY() );

                for ( Circle c : _circleList )
                {
                    if ( c.contains( cursor ) ) // ���� �������
                    {
                        // ��������� � ����� �������� ����� �������
                        int leftX = ( _dimension.width - cursor.x > cursor.x ) ? cursor.x : 0;
                        int rightX = ( _dimension.width - cursor.x > cursor.x ) ? _dimension.width : cursor.x;
                        int upY = ( _dimension.height - cursor.y > cursor.y ) ? cursor.y : 0;
                        int downY = ( _dimension.height - cursor.y > cursor.y ) ? _dimension.height : cursor.y;

                        // ����� ��� ������
                        int jumpX = _random.nextInt( rightX - leftX - c.diameter ) + leftX;
                        int jumpY = _random.nextInt( downY - upY - c.diameter ) + upY;

                        // ������� ����
                        c.moveTo( jumpX, jumpY );

                        // �����������
                        paint( getGraphics() );
                    }
                }
            }
        }
        );

        // fixme �������� ���������� resize
    }

    public void stop()
    {
        removeMouseMotionListener( getMouseMotionListeners()[0] );
    }

    public void init()
    {
        // �������������� ����� ������
        _random = new Random( System.currentTimeMillis() );

        // ������������� ���������� ������
        rebuild( getSize().width, getSize().height );
    }

    /**
     * �������� ��� ����� ������ ������
     *
     * @param width  ����� ������ �������
     * @param height ����� ������ �������
     */
    private void rebuild( int width, int height )
    {
        // ������ ����� ������ ������
        _circleList = new ArrayList<Circle>();

        // ������� ������ ������ �����������
        int h = height / _diameter;
        int w = width / _diameter;

        // ������� ������ ����� ��������
        int num = h * w / 4;

        // ��������� ������ �������
        for ( int i = 0; i < num; i++ )
        {
            // ���������� ������������ ��� ������ �����
            int wi = _random.nextInt( width - 2 * _diameter );
            int he = _random.nextInt( height - 2 * _diameter );

            // ���������� ���� ��� ������ �����
            int red = _random.nextInt( 255 );
            int green = _random.nextInt( 255 );
            int blue = _random.nextInt( 255 );
            Color color = new Color( red, green, blue );

            // ��������� � ������
            _circleList.add( new Circle( wi, he, _diameter, color ) );
        }
    }

    public void paint( Graphics g )
    {
        // �������
        Dimension dim = getSize();

        // fixme �������� ������������� onresize
        if ( !dim.equals( _dimension ) )
        {
            rebuild( dim.width, dim.height );
            _dimension = dim;
        }

        // �������
        g.clearRect( 0, 0, dim.width - 1, dim.height - 1 );

        // ������ �����
        for ( Circle c : _circleList )
        {
            c.draw( g );
        }
    }
}
