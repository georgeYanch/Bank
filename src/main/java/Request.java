

/**Базовая сущность данной модели,
 * атрибут, принадлежащий каждому объекту
 * типа {@link Client}.
 * 
 * @author George Luckyanchikov
 * @version 1.0
 * @see Client
 *
 */
public class Request {
    private int priority;
    private int inTime;
    private int outTime; 
    
    public Request(int priority, int inTime, int outTime) {
        this.priority = priority;
        this.inTime = inTime;
        this.outTime = outTime;
    }
    
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority){
    	this.priority = priority;
    }
    public void setInTime(int inTime) {
        this.inTime = inTime;
    }
    public void setOutTime(int outTime) {
        this.outTime = outTime;
    }
    public int getInTime() {
        return inTime;
    }
    public int getOutTime() {
        return outTime;
    }
    @Override
    public String toString(){
    	return "Заявка с " + "приоритетом " + priority + "поступила спустя " + inTime + 
    			"минут " + "рассмотрена за " + outTime + " минут.";
    }
}

