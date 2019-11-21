

/**Сущность, обрабатывающася объектами типа {@link Clerk},
 * а также входящая в очередь-атибут класса {@link ClientQueue}
 * 
 * @author George Luckyanchikov
 * @version 1.0
 * @see ClientQueue
 * @see Clerk
 * @see Request
 */

public  class Client {
    private Request request;
    private int id;
	
	public Client(Request request){
		this.request = request;
		id = (int) this.hashCode();
	}
	public int getId(){
		return id;
	}
	public Request getRequest(){
		return request;
	}
	public String toString(){
		return "Клиент с id_" + getId() + " с приоритетом заявки " + request.getPriority() + 
		" поступил спустя " + request.getInTime() + " мин., обслужен за " + request.getOutTime() + " мин." ;
	}
	
}
