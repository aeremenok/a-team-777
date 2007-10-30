import java.awt.*;

/**
 * Цветной круг
 */
public class Circle
{
    public int x;
    public int y;
    public int diameter;
    public Color color;

    Circle(
            int x,
            int y,
            int diameter,
            Color color )
    {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
    }

    /**
     * рисует текущий круг
     *
     * @param g контекст
     */
    public void draw( Graphics g )
    {
        Color c = g.getColor();
        g.setColor( color );
        g.fillOval( x, y, diameter, diameter );
        g.setColor( c );
    }

    /**
     * проверяет точку на принадлежность кругу
     *
     * @param p точка для проверки
     *
     * @return true, если точка принадлежит кругу, false иначе
     */
    public boolean contains( Point p )
    {
        // центр окружности
        int xCenter = x + diameter / 2;
        int yCenter = y + diameter / 2;

        // разница по координатам
        int xDiff = p.x - xCenter;
        int yDiff = p.y - yCenter;

        // растояние от точки до точки
        int distance = (int) Math.sqrt( ( xDiff * xDiff ) + ( yDiff * yDiff ) );

        return distance <= diameter / 2;
    }

    /**
     * перемещает в точку с заданными координатами
     *
     * @param x абсцисса новой точки
     * @param y ордината новой точки
     */
    public void moveTo(
            int x,
            int y )
    {
        this.x = x;
        this.y = y;
    }

    /**
     * проверка на пересечение с другим кругом
     *
     * @param c круг для проверки
     *
     * @return true если пересечение есть, false иначе
     */
    public boolean intersectsWith( Circle c )
    {
        int diffX = this.x - c.x;
        int diffY = this.y - c.y;

        int distance = (int) Math.sqrt( ( diffX * diffX ) + ( diffY * diffY ) );

        return distance < ( c.diameter + this.diameter ) / 2;
    }
}
