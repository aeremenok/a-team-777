/**
 * @author lysenko
 *
 */
public class HoareSort extends SortMethod {

	/**
	 * @param array - сортируемый массив
	 */
	public HoareSort( short[] array ) {
		super(array);
	}

	/**
	 * Выполнить сотрировку массива методом Хоара
	 * 
	 * Быстрая сортировка использует стратегию «разделяй и властвуй».
	 * Шаги алгоритма таковы:
	 * 1) Выбираем в массиве некоторый элемент, который будем называть опорным
	 * элементом.
	 * 2) Операция разделения массива: реорганизуем массив таким образом, чтобы
	 * все элементы, меньшие или равные опорному элементу, оказались слева от
	 * него, а все элементы, большие опорного — справа от него.
	 * 3) Рекурсивно упорядочиваем подсписки, лежащие слева и справа от
	 * опорного элемента.
	 * Базой рекурсии являются списки, состоящие из одного или двух элементов,
	 * которые уже упорядочены. Алгоритм всегда завершается, поскольку за
	 * каждую итерацию он ставит по крайней мере один элемент на его
	 * окончательное место.
	 */
	protected void sort() throws InterruptedException {
		quicksort(0, _array.length);
	}
	
	/**
	 * Рекурсивная функция сортировки
	 * @param p
	 * @param r
	 */
	private void quicksort( int p, int r ) {
		
		int q;
	    if (p < r)
	    {
	      q = partition (p, r);
	      quicksort (p, q-1);
	      quicksort (q+1, r);
	    }	    
	}
	
	/**
	 * Операция разделения массива
	 * @param p
	 * @param r
	 * @return
	 */
	private int partition ( int p, int r )
	  {
	    short x = _array[r];   
	    int i = p - 1;
	    short tmp;
	    for (int j = p; j < r; j++)
	      if (_array[j] <= x)
	      {
	        i++;
	        tmp = _array[i];  
	        _array[i] = _array[j];
	        _array[j] = tmp;
	      }
	    
	    tmp = _array[r];
	    _array[r] = _array[i+1];
	    _array[i+1] = tmp;
	    
	    return i + 1;
	  }
	
	/**
	 * @return Имя метода
	 */
	protected String getMethodName(){
		return "Hoare Sort (quicksort)";
	}

}
