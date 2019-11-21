/**Немутабельный класс, содержащий случайные величины в диапазоне.
 * 
 * @author George Luckyanchokov
 * @version 1.0
 * @see Time
 *
 */
public final class RandomScales {
	/**Время поступления следующего клиента в интервале от 0 до 10*/
	static int RANGE_IN = (int)(Math.random()*10);
	/**Время обслуживания клиента в интервале от 2 до 30*/
    static int DURATION  = 2 + (int)(Math.random()*30);
	/**Выручка с проведенной операции в интервале от 1 до 50 тыс.*/
	static int MONEY = 1 + (int)(Math.random()*50001);
	/**Присвоение новых значений случайным величинам*/
	public static void refresh(){
		RANGE_IN = (int)(Math.random()*11);
		DURATION  = 2 + (int)(Math.random()*29);
		MONEY = 1 + (int)(Math.random()*50001);
	}
}
