

/**��������, ��������������� ��������� ���� {@link Clerk},
 * � ����� �������� � �������-������ ������ {@link ClientQueue}
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
		return "������ � id_" + getId() + " � ����������� ������ " + request.getPriority() + 
		" �������� ������ " + request.getInTime() + " ���., �������� �� " + request.getOutTime() + " ���." ;
	}
	
}
