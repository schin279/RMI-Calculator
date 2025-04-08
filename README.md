This project implements a **Remote Method Invocation (RMI)** based **calculator server** in Java. It demonstrates how distributed systems operate using Java RMI, focusing on remote method calls, synchronisation, and client-server communication.

---

## Remote Methods

The server implements the following **remote operations** via the 'Calculator' interface:

| Method | Description |
|--------|-------------|
| `void pushValue(int val)` | Pushes a value onto the stack. |
| `void pushOperation(String operator)` | Pushes an operation ("min", "max", "lcm", "gcd"). Applies the operator to all current stack values, then pushes result. |
| `int pop()` | Pops and returns the top value from the stack. |
| `boolean isEmpty()` | Returns whether the stack is empty. |
| `int delayPop(int millis)` | Delays for `millis` milliseconds before popping a value. |

Operations are assumed to be valid (e.g., no invalid inputs or empty pops).

---
