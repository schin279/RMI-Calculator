import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

/* Implements the Calculator interface and provides remote methods to manipulate a stack of integer values */
public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private Map<String, Stack<Integer>> stacks;

    /* Constructor to initialise the stack.
     * 
     * initialises the map to hold stacks for different clients
     * throws RemoteException if invocation fails
     */
    public CalculatorImplementation() throws RemoteException {
        stacks = new HashMap<>();
    }

    /* Gets the stack associated with the given client ID.
     * 
     * 'clientID': the unique identifier for the client
     * if no stack exists for the client, create a new one
     * retuns the stack associated with the client
     */
    private synchronized Stack<Integer> getStack(String clientID) {
        return stacks.computeIfAbsent(clientID, k -> new Stack<>());
    }

    /* Pushes an integer value onto the stack of the specified client.
     * 
     * 'clientID': the unique identifier for the client
     * 'val': the integer value to push onto the stack
     * throws RemoteException if invocation fails
     */
    public synchronized void pushValue(String clientID, int val) throws RemoteException {
        getStack(clientID).push(val);
    }

    /* Pushes an operation onto the stack and processes the stack values.
     * 
     * 'clientID': the unique identifier for the client
     * 'operator': the operation to apply on the stack
     * List of operations:
     * "min" - push the min value of all the popped values;
     * "max" - push the max value of all the popped values;
     * "lcm" - push the least common multiple of all the popped values;
     * "gcd" - push the greatest common divisor of all the popped values;
     * throws RemoteException if invocation fails
     * clears the stack and pushes the results of the operation
     */

    public synchronized void pushOperation(String clientID, String operator) throws RemoteException {
        Stack<Integer> stack = getStack(clientID);
        int result = 0;
        switch(operator) {
            case "min":
                result = stack.stream().min(Integer::compare).orElse(0);
                break;
            case "max":
                result = stack.stream().max(Integer::compare).orElse(0);
                break;
            case "lcm":
                result = lcm(stack);
                break;
            case "gcd":
                result = gcd(stack);
                break;
        }
        stack.clear();
        stack.push(result);
    }

    /* Pops the top value off the stack for the specified client.
     * 
     * 'clientID': the unique identifier for the client
     * returns the integer value popped from the stack
     * throws RemoteException if invocation fails
     */
    public synchronized int pop(String clientID) throws RemoteException {
        return getStack(clientID).pop();
    }

    /* Checks if the stack is empty for the specified client.
     * 
     * 'clientID': the unique identifier for the client
     * if empty, return true. Otherwise, false.
     * throws RemoteException if invocation fails
     */
    public synchronized boolean isEmpty(String clientID) throws RemoteException {
        return getStack(clientID).isEmpty();
    }

    /* Pops the top value off the stack for the specified client after a delay.
     * 
     * 'clientID': the unique identifier for the client
     * 'millis': the delay in milliseconds before popping the value
     * returns the integer value popped from the stack
     * throws RemoteException if invocation fails
     */
    public synchronized int delayPop(String clientID, int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return pop(clientID);
    }

    /* Finds the greatest common divisor (gcd) for values in the stack.
     * 
     * 'stack': the stack containing integer values
     * returns the gcd of the values
     */
    private int gcd(Stack<Integer> stack) {
        return stack.stream().reduce(this::gcd).orElse(0);
    }

    /* Finds the least common multiple (lcm) for values in the stack.
     * 
     * 'stack': the stack containing integer values
     * returns the lcm of the values
     */
    private int lcm(Stack<Integer> stack) {
        return stack.stream().reduce(1, this::lcm);
    }

    /* Finds the gcd of two integers.
     * 
     * 'a': the first integer
     * 'b': the second integer
     * returns the gcd of a and b
     */
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /* Finds the lcm of two integers.
     * 
     * 'a': the first integer
     * 'b': the second integer
     * returns the lcm of a and b
     */
    private int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }
}