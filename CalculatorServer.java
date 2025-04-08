import java.rmi.registry.*;

/* Creates and binds the Calculator service to the RMI registry */ 
public class CalculatorServer {

    /* Main method:
     * Creates the RMI registry on port 1099
     * Creates an instance of CalculatorImplementation
     * Binds the instance to the registry with the name "CalculatorService"
     * Catches and handles any exceptions thrown during registry creation, binding, or server startup
     */
    public static void main(String[] args) {
        try {
            /* create the RMI registry on port 1099 */
            Registry registry = LocateRegistry.createRegistry(1099);

            /* create an instance of CalculatorImplementation */ 
            Calculator calculator = new CalculatorImplementation();

            /* bind the calculator instance to the registry */
            registry.bind("CalculatorService", calculator);

            /* prints a success message */
            System.out.println("Calculator Server is running.");
        } catch (Exception e) {
            /* prints an error message in case of failure */
            System.out.println("Calculator Server failed to start: " + e);
        }
    }
}
