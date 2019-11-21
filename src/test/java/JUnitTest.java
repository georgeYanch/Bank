import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JUnitTest {
    Clerk clerk, clerk2;
    Client client;
    Request request;
    ClientQueue queue;
    Department department;
    FileWriter fw, fwErr;
    ClientQueue queueCredits = new ClientQueue();
    ClientQueue queueService = new ClientQueue();
    ClientQueue queueInvest = new ClientQueue();

    int amountOfClerks;
    int minQueueSize;
    int maxQueueSize;


    @Before
    public void init() throws IOException {

        // Initializing
        Time.resetTime();
        request = new Request(1, 1, 2);
        client = new Client(request);
        fw = new FileWriter(new File("test.txt"));
        fwErr = new FileWriter(new File("IOtest.log"));
        clerk = new Clerk(1, client, 10, fw);
        clerk2 = new Clerk(2, client, 15, fw);
        queue = new ClientQueue();
        department = new Department("test");
        department.setClerk(clerk,1);

        // Optional parameters of modelling
        amountOfClerks = 5;
        minQueueSize = 3;
        maxQueueSize = 7;

        queueCredits = new ClientQueue();
        queueService = new ClientQueue();
        queueInvest = new ClientQueue();
    }

    @Test
    public void time_Is_Reseting(){
        Time.resetTime();
        Assert.assertTrue(Time.getTime().equals("8:00"));
    }

    @Test(timeout = 100)
    public void service_Is_non_Interruptive(){
        clerk.run();
        Assert.assertTrue(!clerk.currentThread().isInterrupted());
    }

    @Test
    public void time_Flow_Is_Ok() {
        Time.addLocalTime(60);
        Assert.assertTrue(Time.getTime().equals("9:00"));
    }

    @Test(expected = InterruptedException.class)
    public void interruption_Is_fatal() throws IOException, InterruptedException {

        BasicConfigurator.configure();
        Department departmentCredits = new Department("Кредитный Отдел");
        Time.incDay();
        Time.resetTime();

        queueCredits.addClient(new Client(new Request(1, RandomScales.RANGE_IN, RandomScales.DURATION)));
        departmentCredits.setClerk(new Clerk(1, queueCredits.removeClient(), RandomScales.MONEY,fw), 1);
        departmentCredits.getClerk(1).start();
        departmentCredits.getClerk(1).interrupt();
        if(departmentCredits.getClerk(1).isInterrupted()) throw new InterruptedException();
        departmentCredits.freeClerk(1);
        RandomScales.refresh();

    }


    @Test
    public void queue_Is_Working(){
        queue.addClient(client);
        Assert.assertEquals(queue.size(), 1);
    }

    @Test
    public void scales_Are_Normally_Srpeaded(){
        int a = RandomScales.RANGE_IN;
        int b = RandomScales.DURATION;
        int c = RandomScales.MONEY;

        RandomScales.refresh();

        Assert.assertTrue((a != RandomScales.RANGE_IN) &&
                                    (b != RandomScales.DURATION) &&
                                    (c != RandomScales.MONEY)
        );
    }

    @Test(expected = InputMismatchException.class)
    public void input_Mismatch_Is_Fatal() {

        amountOfClerks = 0;
        minQueueSize = -30;
        maxQueueSize = -9;

        boolean t = true;
        if(amountOfClerks <= 0) throw new InputMismatchException();
        if(minQueueSize <= 0) throw new InputMismatchException();
        if(maxQueueSize <= minQueueSize) throw new InputMismatchException();
    }

    @Test
    public void department_Administation_Workers_Are_Free(){

        // Arrange
        department.setClerk(clerk,1);
        department.setClerk(clerk2,2);

        // Act
        department.freeClerk(1);
        department.freeClerk(2);

        // Assert
        Assert.assertTrue((department.getServed() == 3) && (department.getNotBusy() == 2));

    }
}
