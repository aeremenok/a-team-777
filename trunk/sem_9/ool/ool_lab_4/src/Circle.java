import java.awt.*;

/**
 * ������� ����
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
     * ������ ������� ����
     *
     * @param g ��������
     */
    public void draw( Graphics g )
    {
        Color c = g.getColor();
        g.setColor( color );
        g.fillOval( x, y, diameter, diameter );
        g.setColor( c );
    }

    /**
     * ��������� ����� �� �������������� �����
     *
     * @param p ����� ��� ��������
     *
     * @return true, ���� ����� ����������� �����, false �����
     */
    public boolean contains( Point p )
    {
        // ����� ����������
        int xCenter = x + diameter / 2;
        int yCenter = y + diameter / 2;

        // ������� �� �����������
        int xDiff = p.x - xCenter;
        int yDiff = p.y - yCenter;

        // ��������� �� ����� �� �����
        int distance = (int) Math.sqrt( ( xDiff * xDiff ) + ( yDiff * yDiff ) );

        return distance <= diameter / 2;
    }

    /**
     * ���������� � ����� � ��������� ������������
     *
     * @param x �������� ����� �����
     * @param y �������� ����� �����
     */
    public void moveTo(
            int x,
            int y )
    {
        this.x = x;
        this.y = y;
    }

    /**
     * �������� �� ����������� � ������ ������
     *
     * @param c ���� ��� ��������
     *
     * @return true ���� ����������� ����, false �����
     */
    public boolean intersectsWith( Circle c )
    {
        int diffX = this.x - c.x;
        int diffY = this.y - c.y;

        int distance = (int) Math.sqrt( ( diffX * diffX ) + ( diffY * diffY ) );

        return distance < ( c.diameter + this.diameter ) / 2;
    }
}
