# Singleton Design Pattern (Java)

##  

The **Singleton Design Pattern** ensures that a class has **only one instance** and provides a **global point of access
** to it.

In simple terms:
> Only one object of a class is created for the entire JVM.

> Singleton = exactly ONE instance of a class for the entire JVM + global access point.

**Core Rules of Singleton**

- A class must:
- Have private constructor
- Have private static instance
- Expose public static getInstance()

---

## When to Use Singleton

Use Singleton when:

- Exactly **one object** is required
- Shared access is needed across the application
- Object creation is **expensive**
- Centralized control is required

---

## ️ Key Characteristics

- Private constructor
- Static instance variable
- Public static method to return the instance
- Instance is created only once

---

## Basic Singleton (Lazy Initialization – NOT Thread Safe)

```java
public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

## Q1. What is Singleton Pattern and Why Use It?

### What is it?

The **Singleton Design Pattern** ensures that **only one instance of a class exists per JVM** and provides a **global
access point** to that instance.

### Why use it?

- To control object creation
- To share a single resource across the application
- To avoid expensive repeated object creation
- To maintain consistency (single source of truth)

### Typical use cases:

- Logger
- Database connection pool
- Configuration manager
- Cache manager
- Thread pool

---

## Q2. Difference Between Eager and Lazy Initialization

| Aspect          | Eager Initialization   | Lazy Initialization          |
|-----------------|------------------------|------------------------------|
| Object creation | At class loading time  | When first requested         |
| Performance     | Faster access          | Saves memory initially       |
| Resource usage  | May waste resources    | More efficient               |
| Thread safety   | Thread-safe by default | Not thread-safe by default   |
| Use when        | Object is lightweight  | Object creation is expensive |

---

## Q3. How to Make Singleton Thread-Safe?

### Approaches:

1. **Synchronized method**
2. **Double-checked locking**
3. **Eager initialization**
4. **Enum-based Singleton**

### Example (synchronized):

```java
public static synchronized Singleton getInstance() {
    if (instance == null) {
        instance = new Singleton();
    }
    return instance;
}
```

## Q4. What is double-checked locking and why volatile is needed?

It's a pattern that checks if the instance is null twice to avoid unnecessary synchronization:

```java
if(instance ==null){           // 1st check (no lock - fast)
synchronized (Class .class){   // Lock only if needed
        if(instance ==null){    // 2nd check (with lock)
instance =new

Singleton();
}
        }
        }
```

**Why Two Checks?**
First check (outside lock): Avoids locking overhead after instance is created. Most calls just return the existing
instance without any synchronization.
Second check (inside lock): Prevents race condition. Multiple threads might pass the first check simultaneously, so we
need to check again inside the lock.

**Why volatile is Needed?**
javaprivate static volatile Singleton instance;
The Problem Without volatile:
Creating an object has 3 steps:

- Allocate memory
- Initialize object
- Assign reference to variable

The JVM can reorder these steps for optimization:

- Allocate memory
- Assign reference to variable ← Instance appears "not null"
- Initialize object ← But not fully constructed!

Another thread might see a "not null" instance that's actually half-constructed and crash.
With volatile:

- Prevents instruction reordering
- Ensures visibility across threads
- Guarantees the instance is fully constructed before being visible to other threads

---