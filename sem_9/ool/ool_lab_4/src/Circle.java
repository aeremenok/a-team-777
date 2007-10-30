import java.awt.*;

/**
 * Цветной круг
 */
public class Circle
{
    /**
     * абсцисса левого верхнего угла
     */
    public int x;

    /**
     * ордината левого верхнего угла
     */
    public int y;

    /**
     * диаметр круга
     */
    public int diameter;

    /**
     * цвет круга
     */
    public Color color;

    /**
     * конструктор круга
     *
     * @param x        абсцисса левого верхнего угла
     * @param y        ордината левого верхнего угла
     * @param diameter диаметр
     * @param color    цвет
     */
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
        // сохраняем системный цвет
        Color c = g.getColor();

        // устанавливаем свой
        g.setColor( color );

        // рисуем
        g.fillOval( x, y, diameter, diameter );

        // возвращаем цвет обратно
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

        // расстояние меньше радиуса - точка принадлежит кругу
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
        // центр нашего круга
        int thisCenterX = this.x + this.diameter / 2;
        int thisCenterY = this.y + this.diameter / 2;

        // центр другого круга
        int cCenterX = c.x + c.diameter / 2;
        int cCenterY = c.y + c.diameter / 2;

        // растояния между центрами
        int diffX = thisCenterX - cCenterX;
        int diffY = thisCenterY - cCenterY;

        // расстояние между центрами по прямой
        int distance = (int) Math.sqrt( ( diffX * diffX ) + ( diffY * diffY ) );

        // расстояние меньше суммы радиусов - пересечение есть
        return distance < ( c.diameter + this.diameter ) / 2;
    }
}
