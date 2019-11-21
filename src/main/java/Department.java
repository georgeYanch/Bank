
import java.util.LinkedList;

/**Класс, оперирующий связным списком объектов типа {@link Clerk}.
 * Содержит поля, служащие для отображения финальной статистики.
 * 
 * @author George Luckyanchikov 
 * @version 1.0
 * @see Clerk
 * @throws NullPointerException
 */
public class Department{

	private LinkedList<Clerk> list;
	private String specialization;
	private int globalIncome;
	private int servedClients;
	private int lostClients;
	private int notBusy;
	
	public Department(String specialization){
		this.specialization = specialization;
		list = new LinkedList<Clerk>();
		globalIncome = 0;
		servedClients = 0;
	}

	public String getSpecialization(){
		return specialization;
	}

	public int getLost(){
		return lostClients;
	}


	public void setClerk(Clerk clerk, int number){
		list.add(number - 1, clerk);
		servedClients++;
	}
	public Clerk getClerk(int number){
		return list.get(number - 1);
	}
	public void freeClerk(int number){
		list.set(number - 1, null);
		addNotBusy();
	}
	public void accrual(int income){
		globalIncome += income;
	}
	public void addLostClients(int lostClients){
		this.lostClients += lostClients;
	}
	public int getServed(){
		return servedClients;
	}
	public void addNotBusy(){
		notBusy++;
	}
	public int getNotBusy(){
		return notBusy;
	}
	public int getGlobalIncome(){
		return globalIncome;
	}
}
