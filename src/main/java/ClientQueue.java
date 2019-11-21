import java.util.LinkedList;
import java.util.Queue;

/**Класс, оперирующий очередью объектов типa {@link Client}.
 * 
 * @author George Luckyanchikov
 * @version 1.0
 * @see Client
 *
 */
public  class ClientQueue {
	
    private Queue<Client> queue = new LinkedList<Client>();
    
    public ClientQueue(){
    	//queue = null;
    }
    public void addClient(Client client) {
    	queue.add(client);
    }
    public Client removeClient(){
    	return queue.remove();
    }
    public Client getClient(){
    	return queue.peek();
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    public int size(){
    	return queue.size();
    }
   
}

   