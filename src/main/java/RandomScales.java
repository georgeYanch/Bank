/**������������� �����, ���������� ��������� �������� � ���������.
 * 
 * @author George Luckyanchokov
 * @version 1.0
 * @see Time
 *
 */
public final class RandomScales {
	/**����� ����������� ���������� ������� � ��������� �� 0 �� 10*/
	static int RANGE_IN = (int)(Math.random()*10);
	/**����� ������������ ������� � ��������� �� 2 �� 30*/
    static int DURATION  = 2 + (int)(Math.random()*30);
	/**������� � ����������� �������� � ��������� �� 1 �� 50 ���.*/
	static int MONEY = 1 + (int)(Math.random()*50001);
	/**���������� ����� �������� ��������� ���������*/
	public static void refresh(){
		RANGE_IN = (int)(Math.random()*11);
		DURATION  = 2 + (int)(Math.random()*29);
		MONEY = 1 + (int)(Math.random()*50001);
	}
}
