import java.rmi.registry.*;
import java.util.concurrent.atomic.AtomicInteger;;

public class CalculatorClient implements Runnable {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private final int ID;
    private final Calculator calculator;

    /* Constructor to initialise the client with a unique ID and the Calculator reference.
     * 
     * 'calculator': the remote Calculator object
     */
    public CalculatorClient(Calculator calculator) {
        this.ID = counter.getAndIncrement();
        this.calculator = calculator;
    }

    /* The main logic for the CalculatorClient.
     * Tests that pushValue and pop works.
     * Tests the min, max, gcd, lcm, and delayPop operations.
     * Prints the results.
     * Handles exceptions by printing the error message.
     */
    @Override
    public void run() {
        try {
            String clientID = String.valueOf(ID);
            
            /* tests the min operation */
            calculator.pushValue(clientID, 10);
            calculator.pushValue(clientID, 30);
            calculator.pushValue(clientID, 50);
            calculator.pushOperation(clientID, "min");
            System.out.println("Client " + clientID + " - Min value: " + calculator.pop(clientID));

            /* tests the max operation */
            calculator.pushValue(clientID, 10);
            calculator.pushValue(clientID, 30);
            calculator.pushValue(clientID, 50);
            calculator.pushOperation(clientID, "max");
            System.out.println("Client " + clientID + " - Max value: " + calculator.pop(clientID));

            /* tests the gcd operation */
            calculator.pushValue(clientID, 10);
            calculator.pushValue(clientID, 30);
            calculator.pushValue(clientID, 50);
            calculator.pushOperation(clientID, "gcd");
            System.out.println("Client " + clientID + " - Greatest common divisor: " + calculator.pop(clientID));

            /* tests the lcm operation */
            calculator.pushValue(clientID, 10);
            calculator.pushValue(clientID, 30);
            calculator.pushValue(clientID, 50);
            calculator.pushOperation(clientID, "lcm");
            System.out.println("Client " + clientID + " - Least common multiple: " + calculator.pop(clientID));

            /* tests the delayPop operation */
            calculator.pushValue(clientID, 10);
            calculator.pushValue(clientID, 30);
            calculator.pushValue(clientID, 50);
            System.out.println("Client " + clientID + " - Popping in 2 seconds...");
            long startTime = System.currentTimeMillis();
            int result = calculator.delayPop(clientID, 2000);
            long endTime = System.currentTimeMillis();
            System.out.println("Client " + clientID + " - Result after delay: " + result);
            System.out.println("Client " + clientID + " - Actual delay: " + (endTime - startTime) + " milliseconds");
        } catch (Exception e) {
            System.out.println("Client " + ID + " failed: " + e);
        }
    }

    /* Main method:
     * Sets up the RMI registry and looks up the Calculator service.
     * Starts multiple client threads to test the service concurrently.
     */
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Calculator calculator = (Calculator) registry.lookup("CalculatorService");

            /* create and start multiple client threads */
            Thread client1 = new Thread(new CalculatorClient(calculator));
            Thread client2 = new Thread(new CalculatorClient(calculator));
            Thread client3 = new Thread(new CalculatorClient(calculator));
            Thread client4 = new Thread(new CalculatorClient(calculator));

            client1.start();
            client2.start();
            client3.start();
            client4.start();

            /* waits for all threads to complete */
            client1.join();
            client2.join();
            client3.join();
            client4.join();
        } catch (Exception e) {
            System.out.println("Calculator Client failed: " + e);
        }
    }
}
