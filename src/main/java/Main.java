import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**Основной класс с главным потоком, вызывающим дочерние. JVM позволяет выполняться сразу нескольким потокам.
 * Моделирует работу филиала банка за определенный период времени.
 * После консольного ввода данных, проверяющимися на корректность, записывает в указанные файлы информацию и статистику
 * проведенного моделирования по каждому из трёх банковских отделов.
 * 
 * @author George Luckuanchikov
 * @version 1.0
 * @throws InterruptedException
 * @throws InputMismatchException
 * @throws IOException
 * 
 */
public class Main {
	public static void main(String[]args) throws InterruptedException, IOException{
		BasicConfigurator.configure();
		int amountOfClerks = 0;
		int minQueueSize = 0;
		int maxQueueSize = 0;
		boolean t = true;
/**В данном контексте файлы создаются напрямую в папке с проектом,
 * поэтому фактическое "жёсткое" прописывание их названия(пути) не будет являться
 * анти-паттерном жесткого кодирования (Hardcode), а запуск на любом другом устройстве
 * не приведет к появлению исключений.
 * */
		final FileWriter fwCredits = new FileWriter("statisticsCredits.txt");
		final FileWriter fwService = new FileWriter("statisticsService.txt");
		final FileWriter fwInvest = new FileWriter("statisticsInvest.txt");
		
		Scanner sc = new Scanner(System.in);
		
		Department departmentCredits = new Department("Кредитный Отдел");
		Department departmentService = new Department("Отдел по работе с клиентами");
		Department departmentInvest = new Department("Инвестиционный отдел");
		
		ClientQueue queueCredits = new ClientQueue();
		ClientQueue queueService = new ClientQueue();
		ClientQueue queueInvest = new ClientQueue();

//----------------------------------------------------------------------------------------------------------------------------------
	try{
		System.out.println("Добро пожаловать!");
		System.out.println("\nЗадайте количество работников в отделении банка(оптимально 2-6):");
		amountOfClerks = sc.nextInt();
		if(amountOfClerks <= 0) throw new InputMismatchException();
	}catch(InputMismatchException e){
		t = false;
		System.out.println("Неккоректный ввод! \nВведите целое положительное число!");
	}
	if(t) try{
		System.out.println("Задайте минимальный размер очереди(опимально 1-4):");
		minQueueSize = sc.nextInt();
		if(minQueueSize <= 0) throw new InputMismatchException();
	}catch(InputMismatchException e){
		t = false;
		System.out.println("Неккоректный ввод! \nВведите целое положительное число, большее единицы!");
	}
	if(t) try{
		System.out.println("Задайте максимальный размер очереди:");
		maxQueueSize = sc.nextInt();
		if(maxQueueSize <= minQueueSize) throw new InputMismatchException();
	}catch(InputMismatchException e){
		t = false;
		System.out.println("Неккоректный ввод! \nВведите целое положительное число, большее минимального размера очереди!");
	}
	if(t) System.out.println("Обработка данных...");

//----------------------------------------------------------------------------------------------------------------------------------
	
	while(Time.day <= Time.periodOfModelling){
		int Cr = 0, counterCr = 1;
		Time.incDay();
		Time.resetTime();
		
		if(Time.day % 6 == 0) Time.setWorkDayEnd(14);
		else Time.setWorkDayEnd(16);
		if(Time.day % 7 == 0) continue;
		
	    queueCredits = new ClientQueue();
		fwCredits.write("\r\n------------------------------------------------|День " + 
				 Time.day +  " "  + departmentCredits.getSpecialization() + 
				  "|------------------------------------------------");	
		while(!( (Time.hours >= Time.workDayEnd) && (Time.minutes >= 0) )){
			
				for(int k = 0; k < minQueueSize; k++){
					queueCredits.addClient(new Client(new Request(counterCr, RandomScales.RANGE_IN, RandomScales.DURATION)));
					counterCr++;
					RandomScales.refresh();	
				}
				for(int k = minQueueSize + 1; k < (int)(Math.random()*maxQueueSize + 1); k++){
					queueCredits.addClient(new Client(new Request(counterCr, RandomScales.RANGE_IN, RandomScales.DURATION)));
					counterCr++;
					RandomScales.refresh();	
				}
				for(int f = 1; f < amountOfClerks; f++){
					if(!queueCredits.isEmpty()){
						departmentCredits.setClerk(new Clerk(f, queueCredits.removeClient(), RandomScales.MONEY,fwCredits), f);
						departmentCredits.getClerk(f).start();
						departmentCredits.getClerk(f).join();
						Cr++;
						departmentCredits.accrual(departmentCredits.getClerk(f).getIncome());
						departmentCredits.freeClerk(f);		
						RandomScales.refresh();
					}else break;
			     }
		}
		for(int f = 1; f < amountOfClerks + 1; f++){
			if(departmentCredits.getClerk(f) == null) departmentCredits.addNotBusy();		
		}
		departmentCredits.addLostClients(queueCredits.size());
		fwCredits.write("\r\nПринято клиентов: " + Cr);
		fwCredits.write("\r\nПотеряно клиентов: " + queueCredits.size());
	}
//----------------------------------------------------------------------------------------------------------------------------------

	Time.day = 0;
	while(Time.day <= Time.periodOfModelling){
		int Serv = 0, counterServ = 1;
		Time.incDay();
		Time.resetTime();

		if(Time.day % 6 == 0) Time.setWorkDayEnd(14);
		else Time.setWorkDayEnd(16);
		if(Time.day % 7 == 0) continue;
		
		queueService = new ClientQueue();
		fwService.write("\r\n------------------------------------------------|День " + 
				 Time.day + " " + departmentService.getSpecialization() + 
				  "|------------------------------------------------");	
		while(!( (Time.hours >= Time.workDayEnd) && (Time.minutes >= 0) )){
				for(int k = 0; k < minQueueSize; k++){
					queueService.addClient(new Client(new Request(counterServ, RandomScales.RANGE_IN, RandomScales.DURATION)));
					counterServ++;
					RandomScales.refresh();	
				}
				for(int k = minQueueSize+1; k < (int)(Math.random()*maxQueueSize + 1); k++){
					queueService.addClient(new Client(new Request(counterServ, RandomScales.RANGE_IN, RandomScales.DURATION)));
					counterServ++;
					RandomScales.refresh();	
				}
				for(int f = 1; f < amountOfClerks + 1; f++){
					if(!queueService.isEmpty() ){
						departmentService.setClerk(new Clerk(f, queueService.removeClient(), RandomScales.MONEY,fwService), f);
						departmentService.getClerk(f).start();
						departmentService.getClerk(f).join();
						Serv++;
						departmentService.accrual(departmentService.getClerk(f).getIncome());
						departmentService.freeClerk(f);
						RandomScales.refresh();
					}else break;
			     }
		}
		for(int f = 1; f < amountOfClerks; f++){
			if(departmentService.getClerk(f) == null) departmentService.addNotBusy();
		}
		departmentService.addLostClients(queueService.size());
		fwService.write("\r\nПринято клиентов: " + Serv);
		fwService.write("\r\nПотеряно клиентов: " + queueService.size());
	}
//----------------------------------------------------------------------------------------------------------------------------------

	Time.day = 0;
	while(Time.day <= Time.periodOfModelling){
		int Inv = 0, counterInv = 1;
		Time.incDay();
		Time.resetTime();
		
		if(Time.day % 6 == 0) Time.setWorkDayEnd(14);
		else Time.setWorkDayEnd(16);
		if(Time.day % 7 == 0) continue;
		
		queueInvest = new ClientQueue();
		fwInvest.write("\r\n------------------------------------------------|День " + 
				 Time.day + " " + departmentInvest.getSpecialization() + 
				  "|------------------------------------------------");	
		while(!( (Time.hours >= Time.workDayEnd) && (Time.minutes >= 0) )){
				for(int k = 0; k < minQueueSize; k++){
					queueInvest.addClient(new Client(new Request(counterInv, RandomScales.RANGE_IN, RandomScales.DURATION)));
					counterInv++;
					RandomScales.refresh();	
				}for(int k = minQueueSize+1; k < (int)(Math.random()*maxQueueSize + 1); k++){
					queueInvest.addClient(new Client(new Request(counterInv, RandomScales.RANGE_IN, RandomScales.DURATION)));
					counterInv++;
					RandomScales.refresh();	
				}
				for(int f = 1; f < amountOfClerks + 1; f++){
					if(!queueInvest.isEmpty() ){
						departmentInvest.setClerk(new Clerk(f, queueInvest.removeClient(), RandomScales.MONEY,fwInvest), f);
						departmentInvest.getClerk(f).start();
						departmentInvest.getClerk(f).join();
						Inv++;
						departmentInvest.accrual(departmentInvest.getClerk(f).getIncome());
						departmentInvest.freeClerk(f);		
						RandomScales.refresh();
					}else break;
			     }
		}
		for(int f = 1; f < amountOfClerks; f++){
			if(departmentInvest.getClerk(f) == null) departmentInvest.addNotBusy();
		}
		departmentInvest.addLostClients(queueInvest.size());
		fwInvest.write("\r\nПринято клиентов: " + Inv);
		fwInvest.write("\r\nПотеряно клиентов: " + queueInvest.size());
	}
//----------------------------------------------------------------------------------------------------------------------------------

	fwCredits.write("\r\n" + departmentCredits.getSpecialization() + ": " 
	               + amountOfClerks + " клерка(ов) " + " мин.очередь: " + minQueueSize + " макс.очередь: " + maxQueueSize);
	fwCredits.write("\r\nМесячная прибыль: " + (departmentCredits.getGlobalIncome() - amountOfClerks*300*31) + " гривен");
	fwCredits.write("\r\nКлиентов всего обслужено: " + departmentCredits.getServed());
	fwCredits.write("\r\nКлиентов всего потеряно: " + departmentCredits.getLost());
	fwCredits.write("\r\nПроцент простоя клерков "
				   + Math.round(((float)departmentCredits.getNotBusy()/departmentCredits.getServed())*100) + "%");
	fwCredits.close();
	
	fwService.write("\r\n" + departmentService.getSpecialization() + ": " + amountOfClerks + " клерка(ов) " + " мин.очередь: " + minQueueSize + " макс.очередь: " + maxQueueSize);
	fwService.write("\r\nМесячная прибыль: " + (departmentService.getGlobalIncome() - amountOfClerks*300*31) + " гривен");
	fwService.write("\r\nКлиентов всего обслужено: " + departmentService.getServed());
	fwService.write("\r\nКлиентов всего потеряно: " + departmentService.getLost());
	fwService.write("\r\nПроцент простоя клерков " + Math.round(((float)departmentService.getNotBusy()/departmentService.getServed())*100) + "%");
	fwService.close();
	
	fwInvest.write("\r\n" + departmentInvest.getSpecialization() + ": " + amountOfClerks + " клерка(ов) " + " мин.очередь: " + minQueueSize + " макс.очередь: " + maxQueueSize);
	fwInvest.write("\r\nМесячная прибыль: " + (departmentInvest.getGlobalIncome()  - amountOfClerks*300*31) + " гривен");
	fwInvest.write("\r\nКлиентов всего обслужено: " + departmentInvest.getServed());
	fwInvest.write("\r\nКлиентов всего потеряно: " + departmentInvest.getLost());
	fwInvest.write("\r\nПроцент простоя клерков " + Math.round(((float)departmentInvest.getNotBusy()/departmentInvest.getServed())*100) + "%");
	fwInvest.close();
	
	System.out.println("Моделирование проведено успешно! Проанализируйте полученную статистику.");
	sc.close();
	}
			
}