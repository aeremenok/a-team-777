/**
 * Реализация сортировки на основе бинарного дерева
 * 
 * @author lysenko
 */
public class BinarySort
    extends SortMethod
{

    /**
     * @param array - сортируемый массив
     */
    public BinarySort(
        short[] array )
    {
        super( array );
    }

    /**
     * Выполнить сотрировку массива на основе бинарного дерева
     */
    protected void sort()
        throws InterruptedException
    {

        BinaryTree tree = new BinaryTree( _array[0] );

        // ЗАПОЛНИТЬ БИНАРНОЕ ДЕРЕВО
        for ( int i = 1; i < _array.length; i++ )
        {
            BinaryTree subTree = new BinaryTree( _array[i] );
            tree.add( subTree );
        }

        // ВЫПОЛНИТЬ СОРТИРОВКУ, СОХРАНИВ РЕЗУЛЬТАТ В ИСХОДНОМ МАССИВЕ
        tree.sort( _array );
    }

    /**
     * @return Имя метода
     */
    protected String getMethodName()
    {
        return "Binary tree sort";
    }

}
