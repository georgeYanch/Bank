
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileWriter;
import java.io.IOException;

/**Используется для работы с объектами типа {@link Client},
 * входит в коллекцию-атрибут класса Department. 
 * Наследует класс {@link Thread} с последующими переопределением
 * метода run() его диспетчеризацией и реализацией блоков синхронизации полей.
 * <pre>
 *	{@code public void run(){
	 synchronized(field){
	       	//...
 *			     }
 *			}
 *</pre>
 * 
 * @author George Luckyanchikov
 * @version 1.0
 * @see Department
 * @see Client
 * @see Thread
 * @throws IOEcxeption 
 * @throws InterruptedException
 *
 */
public class Clerk extends Thread {

	private static final Logger log = LogManager.getLogger(Clerk.class);

    private Client client;
    private FileWriter fw;
	private int number;
	private int income;
	public final int salary = 300;
	
    public Clerk(int number, Client client, int income, FileWriter fw){
    	this.number = number;
    	this.client = client;
    	this.income = income;
    	this.fw = fw;

    	log.info("НОВЫЙ КЛИЕНТ");
    }
    public ThreadGroup getThreads(){
    	return this.getThreadGroup();
	}
	public void setIncome(int income){
		this.income = income;
	}
	public int getIncome(){
		return income;
	}
	public int getNumber(){
		return number;
	}
	@Override
	public void run(){
        log.warn("[НАЧАЛО РАБОТЫ ПОТОКА] Обслуживание клиента...");
	   synchronized(client){
	     synchronized(Time.class){
	       synchronized(fw){	
		   
		if(Time.hours >= 16){
		    log.warn("КОНЕЦ РАБОЧЕГО ДНЯ!");
		    return;
        }
				
		if(!Thread.currentThread().isInterrupted()){
		    log.info("Обслуживаем...");
			Time.addLocalTime(client.getRequest().getInTime());
			String s = "|Клерк№ " + this.getNumber() + "| " + client.toString() + " Сделка на " + this.getIncome() + "грн.";
			try{
				fw.write("\r\nВремя прихода: " + Time.getTime());
			    fw.write("\r\n" + s);
				Time.addLocalTime(client.getRequest().getOutTime());
				fw.write("\r\nВремя ухода: " + Time.getTime());
				fw.write("\r\n_______________________________________________________________________________________________________________________");
			}catch (IOException e){
				log.error("Troubles with I/O processing![X]");
				e.printStackTrace();
			}
			log.info(s);
			log.warn("КЛЕРК № " + this.getNumber() + " СВОБОДЕН\n");
		  }else try {
		    log.error("Clerk's tread was interrupted![X]");
			throw new InterruptedException();
		} catch (InterruptedException e) {
			log.fatal("Thread crushed![X]");
			e.printStackTrace();
		}
		   }
	  }
	}
  }
}

