# Thread Lifecycle in Java

## States in Thread Lifecycle

A thread undergoes various states during its execution. These states represent different phases a thread can be in from its creation to termination.

### 1. New
The thread is created but not started yet.

### 2. Runnable
The thread is eligible to run but is not necessarily executing. It transitions to the **Running** state when the CPU scheduler picks it for execution.

### 3. Running
The thread is actively executing its `run()` method.

### 4. Blocked
The thread is waiting to acquire a resource or lock that is currently held by another thread.

### 5. Waiting
The thread is waiting indefinitely for another thread to perform a specific action.

**Examples:**
- `wait()`
- `join()`

### 6. Timed Waiting
The thread is waiting for a specified period of time.

**Examples:**
- `sleep()`
- `join(time)`
- `wait(time)`

### 7. Terminated
The thread has completed its execution or has been stopped.

---

## State Transitions

| From State | To State | Trigger |
|------------|-----------|---------|
| New | Runnable | `start()` is called |
| Runnable | Running | CPU scheduler selects the thread for execution |
| Running | Blocked | Thread waits for a resource or lock |
| Blocked | Runnable | Required resource or lock becomes available |
| Running | Waiting | Thread calls `wait()`, `join()`, etc. |
| Waiting | Runnable | Another thread calls `notify()` or `notifyAll()` |
| Running | Timed Waiting | Thread calls `sleep()`, `wait(time)`, or `join(time)` |
| Timed Waiting | Runnable | Specified waiting time expires |
| Running | Terminated | Thread completes execution or exits |

---

## Thread Lifecycle Diagram

```text
          +------+
          | New  |
          +------+
              |
           start()
              |
              v
        +-----------+
        | Runnable  |
        +-----------+
              |
      CPU Scheduler
              |
              v
        +----------+
        | Running  |
        +----------+
         /    |    \
        /     |     \
       v      v      v
+---------+ +---------+ +---------------+
| Blocked | | Waiting | | Timed Waiting |
+---------+ +---------+ +---------------+
      \         |             /
       \        |            /
        \       |           /
         +------+----------+
                |
                v
          +-----------+
          | Runnable  |
          +-----------+
                |
                v
          +------------+
          | Terminated |
          +------------+
```

---

## Important Note

In Java's official `Thread.State` enum, there is **no separate `RUNNING` state**.

The actual states are:

- `NEW`
- `RUNNABLE`
- `BLOCKED`
- `WAITING`
- `TIMED_WAITING`
- `TERMINATED`

A thread that is actively executing on the CPU is still considered part of the **RUNNABLE** state. The **Running** state is commonly shown in conceptual diagrams to make the lifecycle easier to understand.