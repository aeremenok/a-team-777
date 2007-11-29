/**
 * Бинарное дерево
 * 
 * @author lysenko
 */
public class BinaryTree
{

    /**
     * Правое поддерево
     */
    private BinaryTree  _rightSubtree;

    /**
     * Левое поддерево
     */
    private BinaryTree  _leftSubtree;

    /**
     * Ключ (совпадает со значением корневого элемента дерева)
     */
    private final short _key;

    /**
     * Идентификатор следующего свободного места в массиве-приемнике данных
     */
    private static int  _receiverIndex = 0;

    /**
     * @param k - ключ (значение корневого элемента)
     */
    public BinaryTree(
        short k )
    {
        _key = k;
    }

    /**
     * Добавить поддерево
     * 
     * @param aTree - добавляемое поддерево
     */
    public void add(
        BinaryTree aTree )
    {

        // СРАВНИТЬ КЛЮЧ ДОБАВЛЯЕМОГО ПОДДЕРЕВА (К) С КЛЮЧОМ КОРНЕВОГО УЗЛА (X)

        // ЕСЛИ K >= X
        if ( aTree._key >= _key )
        {
            // РЕКУРСИВНО ДОБАВИТЬ НОВОЕ ДЕРЕВО В ПРАВОЕ ПОДДЕРЕВО
            if ( _rightSubtree != null )
            {
                _rightSubtree.add( aTree );
            }
            else
            {
                _rightSubtree = aTree;
            }
        }
        else

        // РЕКУРСИВНО ДОБАВИТЬ НОВОЕ ДЕРЕВО В ЛЕВОЕ ПОДДЕРЕВО
        if ( _leftSubtree != null )
        {
            _leftSubtree.add( aTree );
        }
        else
        {
            _leftSubtree = aTree;
        }
    }

    /**
     * Выполнить сортировку данных (обход дерева)
     * 
     * @param destArray - массив приемник упорядоченных данных
     */
    public void sort(
        Short[] destArray )
    {

        // ОЧИЩАЕМ ИНДЕКС ПРИЕМНИКА ДЛЯ ВОЗМОЖНОСТИ МНОГОКРАТНОЙ СОРТИРОВКИ
        _receiverIndex = 0;

        try
        {
            // РЕКУРСИВНО ОБОЙТИ ЛЕВОЕ ПОДДЕРЕВО
            if ( _leftSubtree != null )
            {
                _leftSubtree.sort( destArray );
            }

            // ЗАПИСАТЬ ЗНАЧЕНИЕ КОРНЕВОГО УЗЛА В СООТВЕТСВУЮЩУЮ ПОЗИЦИЮ
            // ПРИЕМНИКА
            destArray[_receiverIndex] = _key;
            _receiverIndex++;

            // РЕКУРСИВНО ОБОЙТИ ПРАВОЕ ПОДДЕРЕВО
            if ( _rightSubtree != null )
            {
                _rightSubtree.sort( destArray );
            }

        }
        catch ( ArrayIndexOutOfBoundsException e )
        {
            System.out.println( "\n\n\tWrong array passed as output container!\n\n" );
        }
    }
}
