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
     * датчик случайных чисел
     */
    private Random _random = null;

    /**
     * текущие размеры апплета (для заглушки onresize)
     */
    private Dimension _dimension = null;

    /**
     * минимальный диаметр круга
     */
    private int MIN_DIAMETER = 20;

    /**
     * максимальный диаметр круга
     */
    private int MAX_DIAMETER = 70;

    /**
     * список кругов для отрисовки
     */
    private ArrayList<Circle> _circleList = null;

    public void start()
    {
        _dimension = getSize();

        addMouseMotionListener( new MouseMotionListener()
        {
            public void mouseDragged( MouseEvent e )
            {
                // ничего не делаем
            }

            public void mouseMoved( MouseEvent e )
            {
                // новое положение курсора
                Point cursor = new Point( e.getX(), e.getY() );

                for ( Circle c : _circleList )
                {
                    if ( c.contains( cursor ) ) // надо прыгать
                    {
                        // вычисляем в какую четверть будем прыгать
                        int leftX = ( _dimension.width - cursor.x > cursor.x ) ? cursor.x : 0;
                        int rightX = ( _dimension.width - cursor.x > cursor.x ) ? _dimension.width : cursor.x;
                        int upY = ( _dimension.height - cursor.y > cursor.y ) ? cursor.y : 0;
                        int downY = ( _dimension.height - cursor.y > cursor.y ) ? _dimension.height : cursor.y;

                        // точка для прыжка
                        int jumpX = _random.nextInt( rightX - leftX - c.diameter ) + leftX;
                        int jumpY = _random.nextInt( downY - upY - c.diameter ) + upY;

                        // двигаем круг
                        c.moveTo( jumpX, jumpY );

                        // перерисовка
                        paint( getGraphics() );
                    }
                }
            }
        }
        );

        // fixme добавить обработчик resize
    }

    public void stop()
    {
        removeMouseMotionListener( getMouseMotionListeners()[0] );
    }

    public void init()
    {
        // инициализируем новый датчик
        _random = new Random( System.currentTimeMillis() );

        // пересчитываем количество кругов
        rebuild( getSize().width, getSize().height );
    }

    /**
     * пересчёт под новый размер экрана
     *
     * @param width  новая ширина апплета
     * @param height новая высота апплета
     */
    private void rebuild( int width, int height )
    {
        // создаём новый список кругов
        _circleList = new ArrayList<Circle>();

        // сколько влезет кругов максимально
        int h = 2 * height / ( MIN_DIAMETER + MAX_DIAMETER );
        int w = 2 * width / ( MIN_DIAMETER + MAX_DIAMETER );

        // столько кругов будем рисовать
        int num = h * w / 4;

        // заполняем массив кругами
        for ( int i = 0; i < num; i++ )
        {
            // генерируем диаметр нового круга
            int d = _random.nextInt( MAX_DIAMETER - MIN_DIAMETER ) + MIN_DIAMETER;

            // генерируем расположение для нового круга
            int wi = _random.nextInt( width - 2 * d );
            int he = _random.nextInt( height - 2 * d );

            // генерируем цвет для нового круга
            int red = _random.nextInt( 255 );
            int green = _random.nextInt( 255 );
            int blue = _random.nextInt( 255 );
            Color c = new Color( red, green, blue );

            // сохраняем в массив
            _circleList.add( new Circle( wi, he, d, c ) );
        }
    }

    public void paint( Graphics g )
    {
        // размеры
        Dimension dim = getSize();

        // fixme заглушка неработающего onresize
        if ( !dim.equals( _dimension ) )
        {
            rebuild( dim.width, dim.height );
            _dimension = dim;
        }

        // очистка
        g.clearRect( 0, 0, dim.width - 1, dim.height - 1 );

        // рисуем круги
        for ( Circle c : _circleList )
        {
            c.draw( g );
        }
    }
}
