# Strategy Pattern - Theoretical Overview

## 📖 Definition

The **Strategy Pattern** is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

##  Intent

- Define a family of algorithms
- Encapsulate each algorithm
- Make algorithms interchangeable within that family
- Allow the algorithm to vary independently from the clients that use it

##  Key Concepts

### Core Principle
Instead of implementing a single algorithm directly, the code receives runtime instructions about which algorithm to use from a family of algorithms.

### When to Use Strategy Pattern

Use the Strategy pattern when:

1. **Multiple Related Classes**: You have many related classes that differ only in their behavior
2. **Multiple Algorithms**: You need different variants of an algorithm
3. **Conditional Statements**: You want to avoid complex conditional statements for selecting algorithms
4. **Runtime Selection**: Algorithm selection needs to happen at runtime
5. **Algorithm Independence**: Clients shouldn't know about the complexity of algorithm implementation

##  Structure

### Components

#### 1. **Strategy (Interface)**
- Declares an interface common to all supported algorithms
- Context uses this interface to call the algorithm defined by a ConcreteStrategy

#### 2. **ConcreteStrategy (Implementation)**
- Implements the algorithm using the Strategy interface
- Each ConcreteStrategy implements a different algorithm

#### 3. **Context**
- Maintains a reference to a Strategy object
- May define an interface that lets Strategy access its data
- Configured with a ConcreteStrategy object
- Can switch strategies at runtime

##  UML Class Diagram

```
┌─────────────────┐
│    Context      │
├─────────────────┤
│ - strategy      │────────────┐
├─────────────────┤            │
│ + setStrategy() │            │ uses
│ + execute()     │            │
└─────────────────┘            ▼
                        ┌──────────────┐
                        │  <<interface>>│
                        │   Strategy   │
                        ├──────────────┤
                        │ + algorithm()│
                        └──────────────┘
                               △
                               │ implements
                 ┌─────────────┼─────────────┐
                 │             │             │
        ┌────────┴──────┐ ┌───┴──────┐ ┌───┴──────┐
        │ConcreteStrategy│ │Concrete  │ │Concrete  │
        │       A        │ │Strategy B│ │Strategy C│
        ├───────────────┤ ├──────────┤ ├──────────┤
        │+ algorithm()  │ │+algorithm│ │+algorithm│
        └───────────────┘ └──────────┘ └──────────┘
```

##  How It Works

### Sequence of Operations

1. **Client** creates a Context object
2. **Client** creates a ConcreteStrategy object
3. **Client** configures Context with the ConcreteStrategy
4. **Context** delegates algorithm execution to the Strategy
5. **Strategy** executes the algorithm and returns result
6. **Client** can change the strategy at runtime by setting a new ConcreteStrategy

##  Advantages

### 1. **Open/Closed Principle**
- Open for extension (add new strategies)
- Closed for modification (existing code unchanged)

### 2. **Single Responsibility Principle**
- Separates algorithm implementation from the code that uses it
- Each strategy has one reason to change

### 3. **Runtime Flexibility**
- Algorithms can be switched at runtime
- No need to recompile code

### 4. **Eliminates Conditional Logic**
- Replaces complex if-else or switch statements
- Makes code cleaner and more maintainable

### 5. **Encapsulation**
- Implementation details hidden from clients
- Each algorithm is independent

### 6. **Testability**
- Each strategy can be tested independently
- Easy to mock strategies for testing

##  Disadvantages

### 1. **Increased Number of Objects**
- More classes in the system
- Can be overhead for simple algorithms

### 2. **Client Awareness**
- Clients must be aware of different strategies
- Clients must understand how strategies differ

### 3. **Communication Overhead**
- Strategy and Context may need to communicate
- May pass unnecessary data to strategies that don't need it

### 4. **Complexity**
- Introduces additional abstraction layer
- May be overkill for simple scenarios


##  Real-World Examples

### 1. **Sorting Algorithms**
```
Context: DataSorter
Strategies: BubbleSort, QuickSort, MergeSort
Usage: Choose sorting algorithm based on data size
```

### 2. **Compression Algorithms**
```
Context: FileCompressor
Strategies: ZipCompression, RARCompression, GZipCompression
Usage: Select compression based on file type or user preference
```

### 3. **Navigation/Route Planning**
```
Context: Navigator
Strategies: CarRoute, BikeRoute, WalkingRoute, PublicTransport
Usage: Calculate route based on transportation mode
```

### 4. **Payment Processing**
```
Context: PaymentProcessor
Strategies: CreditCard, DebitCard, PayPal, Bitcoin
Usage: Process payment through different gateways
```

### 5. **Validation Rules**
```
Context: FormValidator
Strategies: EmailValidation, PhoneValidation, PasswordValidation
Usage: Apply different validation rules dynamically
```

##  Design Principles Applied

### 1. **Encapsulate What Varies**
Algorithms vary, so encapsulate them separately from the context

### 2. **Program to Interface, Not Implementation**
Context works with Strategy interface, not concrete classes

### 3. **Favor Composition Over Inheritance**
Context has-a strategy rather than inheriting behavior

### 4. **Depend on Abstractions**
Both Context and ConcreteStrategies depend on Strategy interface

