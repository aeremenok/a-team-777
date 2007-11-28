/**
 * Бинарное дерево
 * @author lysenko
 */
public class BinaryTree {
	
	/**
	 * Правое поддерево
	 */
	private BinaryTree big;
	
	/**
	 * Левое поддерево 
	 */
	private BinaryTree small;
	
	/**
	 * Ключ (совпадает со значением корневого элемента дерева)
	 */
	private short key;
	
	/**
	 * Идентификатор следующего свободного места в массиве-приемнике данных
	 */
	private static int i = 0;
	
	/**
	 * @param k - ключ (значение корневого элемента)
	 */
	public BinaryTree(short k) {
		key = k;
	}
	
	/**
	 * Добавить поддерево 
	 * @param aTree - добавляемое поддерево
	 */
	public void add( BinaryTree aTree) {

		// СРАВНИТЬ КЛЮЧ ДОБАВЛЯЕМОГО ПОДДЕРЕВА (К) С КЛЮЧЁМ КОРНЕВОГО УЗЛА (X)
		
		// ЕСЛИ K >= X
		if ( aTree.key >= key )
		
			// РЕКУРСИВНО ДОБАВИТЬ НОВОЕ ДЕРЕВО В ПРАВОЕ ПОДДЕРЕВО
			if ( big != null ) big.add( aTree );
			
			// ЕСЛИ ПОДДЕРЕВА НЕТ, ТО ВСТАВИТЬ НА ЭТО МЕСТО НОВОЕ ДЕРЕВО
			else big = aTree;

		// ЕСЛИ K < X
		else
		
			// РЕКУРСИВНО ДОБАВИТЬ НОВОЕ ДЕРЕВО В ЛЕВОЕ ПОДДЕРЕВО
			if ( small != null ) small.add( aTree );
			
			// ЕСЛИ ПОДДЕРЕВА НЕТ, ТО ВСТАВИТЬ НА ЭТО МЕСТО НОВОЕ ДЕРЕВО
			else small = aTree;
	}
	
	
	/**
	 * Выполнить сортировку данных (обход дерева)
	 * @param destArray - массив приемник упорядоченных данных
	 */
	public void sort(short[] destArray) {
		
		try {
			// РЕКУРСИВНО ОБОЙТИ ЛЕВОЕ ПОДДЕРЕВО
			if ( small != null)
				small.sort(destArray);
			
			// ЗАПИСАТЬ ЗНАЧЕНИЕ КОРНЕВОГО УЗЛА В СООТВЕТСВУЮЩУЮ ПОЗИЦИЮ ПРИЕМНИКА
			destArray[i] = key;
			i++;
			
			// РЕКУРСИВНО ОБОЙТИ ПРАВОЕ ПОДДЕРЕВО
			if ( big != null )
				big.sort(destArray);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("\n\n\tWrong array passed as output container!\n\n");
		}
	}
}
