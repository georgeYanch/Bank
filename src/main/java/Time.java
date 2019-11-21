/**Класс, имитирующий локальное время
 * 
 * @author George Luckyancikov
 * @version 1.0
 * @see RandomScales
 *
 */
public class Time {
	/** Час окончания работы филиала.*/
    static int workDayEnd = 16;
	static int hours=0, minutes=0, day=0;
	
	/** Период моделирования, представленный в днях.*/
	final static int periodOfModelling = 31;
	
	public Time(){
		hours = 8;
		minutes = 0;
	}
	public static String getTime(){
		if(minutes < 10)
		return hours +":" +"0"+ minutes;
		else return hours +":"+ minutes;
	}
    static void incDay(){
		day++;
	}
	public static void resetTime(){
		new Time();
	}
	public static void setWorkDayEnd(int hours){
		workDayEnd = hours;
	}
	static void addLocalTime(int min){
		if( minutes + min > 59){
			hours++;
			minutes = (minutes + min) - 60;
		}
		else minutes += min;	
	}
}
