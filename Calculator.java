import java.rmi.*;

/* Defines the remote operations invocable by a client */
public interface Calculator extends Remote {
    
    /* Pushes integer value onto the stack. 
     * 
     * 'clientID: the unique identifier for the client'
     * 'val': the integer value to push onto the stack
     * throws RemoteException if invocation fails
    */
    void pushValue(String clientID, int val) throws RemoteException;
    
    /* Pushes an operation onto the stack and processes the stack values.
     * 
     * 'clientID: the unique identifier for the client'
     * 'operator': the opertaion to apply on the stack
     * throws RemoteException if invocation fails
     */
    void pushOperation(String clientID, String operator) throws RemoteException;
    
    /* Pops the top value off the stack.
     * 
     * 'clientID: the unique identifier for the client'
     * throws RemoteException if invocation fails
     */
    int pop(String clientID) throws RemoteException;

    /* Checks if the stack is empty.
     * 
     * 'clientID: the unique identifier for the client'
     * throws RemoteException if invocation fails
     */
    boolean isEmpty(String clientID) throws RemoteException;

    /* Pops the top value off the stack after a delay.
     * 
     * 'clientID: the unique identifier for the client'
     * 'millis': the delay in milliseconds before popping the value
     * throws RemoteException if invocation fails
     */
    int delayPop(String clientID, int millis) throws RemoteException;
}
 