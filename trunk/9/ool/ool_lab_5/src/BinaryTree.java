/**
 * �������� ������
 * @author lysenko
 */
public class BinaryTree {
	
	/**
	 * ������ ���������
	 */
	private BinaryTree big;
	
	/**
	 * ����� ��������� 
	 */
	private BinaryTree small;
	
	/**
	 * ���� (��������� �� ��������� ��������� �������� ������)
	 */
	private short key;
	
	/**
	 * ������������� ���������� ���������� ����� � �������-��������� ������
	 */
	private static int i = 0;
	
	/**
	 * @param k - ���� (�������� ��������� ��������)
	 */
	public BinaryTree(short k) {
		key = k;
	}
	
	/**
	 * �������� ��������� 
	 * @param aTree - ����������� ���������
	 */
	public void add( BinaryTree aTree) {

		// �������� ���� ������������ ��������� (�) � ���ר� ��������� ���� (X)
		
		// ���� K >= X
		if ( aTree.key >= key )
		
			// ���������� �������� ����� ������ � ������ ���������
			if ( big != null ) big.add( aTree );
			
			// ���� ��������� ���, �� �������� �� ��� ����� ����� ������
			else big = aTree;

		// ���� K < X
		else
		
			// ���������� �������� ����� ������ � ����� ���������
			if ( small != null ) small.add( aTree );
			
			// ���� ��������� ���, �� �������� �� ��� ����� ����� ������
			else small = aTree;
	}
	
	
	/**
	 * ��������� ���������� ������ (����� ������)
	 * @param destArray - ������ �������� ������������� ������
	 */
	public void sort(short[] destArray) {
		
		try {
			// ���������� ������ ����� ���������
			if ( small != null)
				small.sort(destArray);
			
			// �������� �������� ��������� ���� � �������������� ������� ���������
			destArray[i] = key;
			i++;
			
			// ���������� ������ ������ ���������
			if ( big != null )
				big.sort(destArray);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("\n\n\tWrong array passed as output container!\n\n");
		}
	}
}
