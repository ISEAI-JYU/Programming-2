## Encapsulation

### Learning objectives

After this chapter, you will:

* Know what visibility modifiers such as `public` and `private` mean.
* Understand that methods are the primary way for objects to communicate.
* Understand the principles and benefits of encapsulation, and how an object's internal implementation differs from its external use.
* Be able to implement programs where objects work together without depending on each other's internal implementation.

![We can drive a car without knowing how the engine works.](./images/auto.png)

***

## Visibility Modifiers

Java provides three main visibility modifiers: `public`, `protected`, and `private`.
Visibility modifiers determine from where the members of a class can be accessed.

If no visibility modifier is explicitly specified, the member receives `package-private` visibility. This means that the member is visible only to classes in the same package.
The table below summarizes the effects of the different visibility modifiers. The *Default* row refers to package-private visibility.

| Modifier    | Meaning   | Class | Package | Subclass | Other Code |
| ----------- | --------- | ----- | ------- | -------- | ---------- |
| `public`    | public    | Yes   | Yes     | Yes      | Yes        |
| `protected` | protected | Yes   | Yes     | Yes      | No         |
| *default*   | –         | Yes   | Yes     | No       | No         |
| `private`   | private   | Yes   | No      | No       | No         |

The first column ("Class") indicates whether the class itself can access the member. As you can see, a class always has access to its own members.
The second column ("Package") indicates whether other classes in the same package can access the member.
The third column ("Subclass") indicates whether subclasses located outside the package can access the member.
The fourth column indicates whether any other code can access the member.

When other programmers—or even your future self—use a class you have created, visibility modifiers help ensure that the class is used in the way it was intended.
As a general rule, a programmer should use the **most restrictive visibility modifier possible**, unless there is a specific reason not to. This helps protect the internal state of the class and prevents both intentional and accidental misuse.
In particular, public attributes should be carefully considered, as they expose the internal state of a class directly to external code. In this course, we aim to design programs so that public attributes—except constants—are not needed.

> [!NOTE]
> Occasionally, this material may use public attributes in examples for brevity and demonstration purposes. However, this is not recommended in production code.

A visibility modifier can be assigned to attributes and methods by adding it to their declaration. Classes can also have visibility modifiers.


```java,ignore
// FILE: Person.java
class Person {
    // The 'private' visibility modifier hides the attribute so that it
    // cannot be accessed from outside the class.
    private String firstName;
    private String lastName;

    // The 'public' visibility modifier allows the method to be called
    // from outside the class.
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
// FILE_END
```


***

## Encapsulation and Cohesion

Encapsulation is one of the most important concepts in object-oriented programming.
It refers to designing classes to be as independent and modular as possible. Each class has its own responsibility, and the information and functionality required for that responsibility are *encapsulated* within the object. Some of this information and functionality can be hidden for internal use only.
To allow access to an object's state, a class typically provides a public interface consisting of methods. This improves maintainability and extensibility by reducing side effects caused by internal changes.

The first principle of encapsulation is:

> Place related data and functionality inside the same structure.

We have already done this by defining classes together with suitable attributes and methods.

*Cohesion* describes how well a class's methods and attributes fit its intended purpose.
The goal when designing classes is to achieve **high cohesion**. The members of a class should be closely related to what the class is meant to represent.
For example, if we create a class representing a car, it may not make sense to include the owner's name, address, and phone number in that class. Owner-related information and functionality would likely belong in a separate class.

Let's create a class `Car` and apply encapsulation principles.
First, we add only attributes and a simple constructor.

```java,ignore
// FILE: Car.java
class Car {
    String model;
    String serialNumber;
    double kilometersDriven;

    public Car(String model, String serialNumber, double kilometersDriven) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.kilometersDriven = kilometersDriven;
    }
}
// FILE_END
```

We would also like to store information about the car's various components. For example, for the engine we might want to store its model and current RPM. For the tires, it would be useful to know at least the model, tire pressure, and perhaps also a type that allows us to distinguish between summer and winter tires.
We *could* add all of this information directly to the `Car` class as attributes, but the number of attributes would become quite large because each tire has its own data. If we used arrays or lists, we would need several of those as well. It is also possible that a car might not always have an engine installed. Tires can also be removed, and the number of tires may vary between different car models.

The car does not really need to be aware of the internal workings of its engine or tires, so it is better to create multiple classes, each responsible for its own data and functionality.

Let's now add `Engine` and `Tire` classes and define suitable attributes and constructors for them.

```java
// FILE: Engine.java
class Engine {
    String model;
    double rpm;

    public Engine(String model, double rpm) {
        this.model = model;
        this.rpm = rpm;
    }
}
// FILE_END
// FILE: Tire.java
class Tire {
    String model;
    String type;
    double tirePressure;

    public Tire(String model, String type, double tirePressure) {
        this.model = model;
        this.type = type;
        this.tirePressure = tirePressure;
    }
}
// FILE_END
```

Next, we add an engine and a list of tires as attributes of the `Car` class. These attributes contain references to `Engine` and `Tire` objects. Remember that references do not point to anything by default, so we must create the objects and assign the references ourselves.
The `Car` object now contains objects from other classes that manage their own responsibilities as part of the overall car. This type of structure is called *composition*. One benefit of composition is that the engine or tires can easily be replaced by assigning the reference attributes to new objects.

Let's also add a small main program that creates a car with default values and prints some of its information. For now, we will still access attribute values directly, which is not considered good practice. We will improve this shortly.

```java
// FILE: Engine.java
class Engine {
    String model;
    double rpm;

    public Engine(String model, double rpm) {
        this.model = model;
        this.rpm = rpm;
    }
}
// FILE_END
// FILE: Tire.java
class Tire {
    String model;
    String type;
    double tirePressure;

    public Tire(String model, String type, double tirePressure) {
        this.model = model;
        this.type = type;
        this.tirePressure = tirePressure;
    }
}
// FILE_END
// FILE: Car.java
import java.util.ArrayList;

class Car {
    String model;
    String serialNumber;
    double kilometersDriven;
    Engine engine;
    ArrayList<Tire> tires = new ArrayList<>();

    public Car(String model, String serialNumber, double kilometersDriven) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.kilometersDriven = kilometersDriven;

        // Add an engine by creating a new Engine object.
        engine = new Engine("M100", 0);

        // Add four tires by creating Tire objects and
        // storing their references in the tires list.
        tires.add(new Tire("T1", "studded", 100.0));
        tires.add(new Tire("T2", "studded", 100.0));
        tires.add(new Tire("T3", "studded", 100.0));
        tires.add(new Tire("T4", "studded", 100.0));
    }
}
// FILE_END
// FILE: main.java
void main() {
    Car car = new Car("ABC", "123A", 0);

    IO.println("Car model: " + car.model);
    IO.println("Car serial number: " + car.serialNumber);

    IO.println("Engine:");
    IO.println("- " + car.engine.model);

    IO.println("Tires:");
    for (Tire tire : car.tires) {
        IO.println("- " + tire.model);
    }
}
// FILE_END
```

The second principle of encapsulation is *hiding* a class's internal information and restricting access to it so that it can only be accessed through a carefully defined *public interface*.
An object may contain a large amount of internal information used to maintain its state, and that information is usually not intended to be viewed or modified directly from outside the object. In fact, all attributes are typically hidden to prevent the object from being accidentally placed into an invalid state through direct modification.

To modify an object's internal state, classes define public methods that can be called from elsewhere in the program. These methods form the public interface mentioned above. All changes to the object's state take place through these methods, allowing invalid changes to be detected and handled appropriately within the method itself.

What attributes a class contains and how they are internally managed are usually implementation details that users of the class do not need to know. The purpose of hiding these implementation details is to make programming easier: when internal details are hidden and the object's state is managed only through its public interface, internal changes can often be made without affecting the code that uses the class. This is one of the greatest benefits of encapsulation.

Let's add a few methods to the `Car` class to provide a simple public interface. We will also hide the attributes so that the car's state can no longer be modified directly. The `Engine` and `Tire` classes remain unchanged for now.

```java
// FILE: Car.java
import java.util.ArrayList;

public class Car {
    private String model;
    private String serialNumber;
    private double kilometersDriven;
    private Engine engine;
    private ArrayList<Tire> tires = new ArrayList<>();

    public Car(String model, String serialNumber, double kilometersDriven) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.kilometersDriven = kilometersDriven;

        // Add an engine by creating a new Engine object.
        engine = new Engine("M100", 0);

        // Add four tires by creating Tire objects and storing
        // references to them in the tires list.
        tires.add(new Tire("TT", "studded", 100.0));
        tires.add(new Tire("TT", "studded", 100.0));
        tires.add(new Tire("TT", "studded", 100.0));
        tires.add(new Tire("TT", "studded", 100.0));
    }

    public void drive(double kilometers) {
        if (kilometers < 0) return;
        this.kilometersDriven += kilometers;
    }

    public void addEngine(Engine engine) {
        this.engine = engine;
    }

    public void addTire(Tire tire) {
        this.tires.add(tire);
    }

    public void printDetails() {
        IO.println("Model: " + model);
        IO.println("Serial number: " + serialNumber);
        IO.println("Kilometers driven: " + kilometersDriven);

        // We will add printing of the engine and tires next.
    }
}
// FILE_END
// FILE: main.java
void main() {
    Car car = new Car("ABC", "123A", 0);
    car.drive(100);
    car.printDetails();
}
// FILE_END
// FILE: Engine.java
class Engine {
    String model;
    double rpm;

    public Engine(String model, double rpm) {
        this.model = model;
        this.rpm = rpm;
    }
}
// FILE_END
// FILE: Tire.java
class Tire {
    String model;
    String type;
    double tirePressure;

    public Tire(String model, String type, double tirePressure) {
        this.model = model;
        this.type = type;
        this.tirePressure = tirePressure;
    }
}
// FILE_END
```

We hid the attributes of the `Car` class using the `private` visibility modifier, and the car's state is now managed through a simple public interface. We also moved the responsibility for printing information into the class itself.
We should make similar changes to the `Engine` and `Tire` classes so that the `Car` class can use their public interfaces instead of relying on their internal implementation details.

```java
// FILE: Car.java
import java.util.ArrayList;

public class Car {
    private String model;
    private String serialNumber;
    private double kilometersDriven;
    private Engine engine;
    private final int maxTires = 4;
    private ArrayList<Tire> tires = new ArrayList<>();

    public Car(String model, String serialNumber, double kilometersDriven) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.kilometersDriven = kilometersDriven;
    }

    public void drive(double kilometers) {
        if (kilometers < 0) return;
        this.kilometersDriven += kilometers;
    }

    public void addEngine(Engine engine) {
        this.engine = engine;
    }

    public void addTire(Tire tire) {
        if (tires.size() < maxTires) {
            tires.add(tire);
        }
    }

    public void printDetails() {
        IO.println("Car model: " + model);
        IO.println("Car serial number: " + serialNumber);
        IO.println("Kilometers driven: " + kilometersDriven);

        IO.println();
        IO.println("Engine:");
        IO.println();

        // Delegate the printing operation to the engine object.
        if (engine != null) {
            engine.printDetails();
        }

        IO.println();
        IO.println("Tires:");

        // Delegate the printing operation to each tire.
        for (Tire tire : tires) {
            IO.println();
            tire.printDetails();
        }
    }
}
// FILE_END
// FILE: Engine.java
public class Engine {
    private String model;
    private double rpm;

    public Engine(String model, double rpm) {
        this.model = model;
        this.rpm = rpm;
    }

    public void printDetails() {
        IO.println("Model: " + model);
        IO.println("RPM: " + rpm);
    }
}
// FILE_END
// FILE: Tire.java
public class Tire {
    private String model;
    private String type;
    private double tirePressure;

    public Tire(String model, String type, double tirePressure) {
        this.model = model;
        this.type = type;
        this.tirePressure = tirePressure;
    }

    public void printDetails() {
        IO.println("Model: " + model);
        IO.println("Type: " + type);
        IO.println("Pressure: " + tirePressure);
    }
}
// FILE_END
// FILE: main.java
void main() {
    Car car = new Car("ABC", "123A", 0);
    car.addEngine(new Engine("M1", 0));
    car.addTire(new Tire("T1", "studded", 10));
    car.addTire(new Tire("T2", "studded", 10));
    car.addTire(new Tire("T3", "studded", 10));
    car.addTire(new Tire("T4", "studded", 10));

    car.drive(100.0);
    car.printDetails();
}
// FILE_END
```

Our `Car` class is now no longer dependent on the internal implementation details of the `Engine` or `Tire` classes.

***

## Responsibility for an Object's State Belongs to the Object Itself

In some examples we have seen so far, attributes were hidden, but they could still be modified almost directly through methods.
This is not entirely in line with the goals of object-oriented programming. A public interface is not intended to serve merely as an intermediate step for modifying an attribute. The idea of a public interface is to send an object a command to perform an action, after which the object carries out that action in the way it considers most appropriate.

A good example would be a game character whose position is represented by the coordinates `x` and `y`. Instead of modifying the character's position through simple `setX` and `setY` methods, we could define a method called `moveTo` that receives target coordinates and allows the character to perform the movement according to its own internal implementation and constraints.
In this case, responsibility for movement belongs to the character itself. For example, if the character is temporarily unable to move, the method can handle the situation internally, and the code calling the method does not need to consider such special cases.

In practice, simple getter and setter methods are often used in production code because designing objects ideally takes time and effort. In some situations, directly modifying a simple attribute through a method may also be perfectly reasonable.
Although such a method may seem unnecessary, its existence still provides an important benefit: the internal implementation of the class can be changed in the future without breaking the code that uses the class.

Unfortunately, we do not have time in this course to cover object-oriented design theory in depth. We recommend studying a dedicated object-oriented programming course for a more thorough understanding of these concepts.

***

## Cooperation Between Objects

Let's review the concepts introduced in this chapter through another example involving object collaboration.
The benefits of objects become much more apparent when we begin building programs consisting of multiple classes that work together. Now that we have learned the principles of encapsulation, we can use them to organize our code more effectively.

Objects can cooperate in many different ways. Objects can contain other objects—or, more precisely, references to other objects. When an object consists of other objects, each contributing its own functionality, this is often called *composition*.
Objects can also call each other's methods to delegate tasks to the object that is responsible for them or communicate in response to events. Objects may additionally contain collections of other objects; for example, Java's built-in data structures are themselves objects that contain collections of objects.

<!-- ======================================================================= -->
Let's look at an example where we want our program to model buildings, rooms located within those buildings, and reservations made for those rooms.
Each building can contain multiple rooms, and each room can contain multiple reservations. For now, we will ignore the possibility of overlapping reservations. We will also keep the classes relatively simple.

First, let's define the required classes: `Building`, `Room`, and `Reservation` and constructors for these classes.

```java,ignore
// FILE: Building.java
import java.util.*;

public class Building {
    private String name;
    private List<Room> rooms = new ArrayList<>();

    public Building(String name) {
        this.name = name;
    }
}
// FILE_END
// FILE: Room.java
import java.util.*;

public class Room {
    private String name;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
// FILE_END
// FILE: Reservation.java
public class Reservation {
    private String reservedBy;
    private int startTime;
    private int duration;

    public Reservation(String reservedBy, int startTime, int duration) {
        this.reservedBy = reservedBy;
        this.startTime = startTime;
        this.duration = duration;
    }
}
// FILE_END
```

Next, let's add methods to the `Building` class for adding rooms. We would like to find a room later based on its name, so a building should not contain multiple rooms with the same name. Therefore, we also need a way to search for a room by name.
To add a room, we only need the room's name.

```java,ignore
// FILE: Building.java
import java.util.*;

public class Building {
    private String name;
    private List<Room> rooms = new ArrayList<>();

    public Building(String name) {
        this.name = name;
    }

    public Room findRoom(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public void addRoom(String roomName) {
        Room room = findRoom(roomName);
        if (room != null) {
            IO.println("Building " + name + " already contains room " + roomName);
            return;
        }
        rooms.add(new Room(roomName));
    }
}
// FILE_END
// FILE: Room.java
import java.util.*;

public class Room {
    private String name;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
// FILE_END
// FILE: Reservation.java
public class Reservation {
    private String reservedBy;
    private int startTime;
    private int duration;

    public Reservation(String reservedBy, int startTime, int duration) {
        this.reservedBy = reservedBy;
        this.startTime = startTime;
        this.duration = duration;
    }
}
// FILE_END
```

Rooms should also support making reservations. To keep the example simple, we will assume that reservations start on the hour. The system might represent room reservations for a single day.

To make a reservation, we need the name of the person making the reservation, the starting hour, and the duration.
Add the following method to the `Room` class:

```java,ignore
public void addReservation(String reservedBy, int startTime, int duration) {
    reservations.add(new Reservation(reservedBy, startTime, duration));
}
```

Next, let's add the ability to create reservations through the `Building` class so that the user does not even need to know that the `Room` class exists.
How the `Building` class stores room information remains an implementation detail hidden from the user.

To add a reservation, we need the room name, the name of the person making the reservation
,the starting hour and duration.

We will also add simple `print` methods to all classes so that we can easily display all rooms and reservations within a building.

```java
// FILE: Building.java
import java.util.*;

public class Building {
    private String name;
    private List<Room> rooms = new ArrayList<>();

    public Building(String name) {
        this.name = name;
    }

    public Room findRoom(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }

        return null;
    }

    public void addRoom(String roomName) {
        Room room = findRoom(roomName);
        if (room != null) {
            IO.println( "Building '" + name + "' already contains room '" + roomName + "'");
            return;
        }
        rooms.add(new Room(roomName));
    }

    public void addReservation(String roomName, String reservedBy, int startTime, int duration) {
        Room room = findRoom(roomName);
        if (room == null) {
            IO.println( "Building '" + name + "' does not contain room '" + roomName + "'");
            return;
        }

        room.addReservation(reservedBy, startTime, duration);
    }

    public void print() {
        IO.println(name);
        for (Room room : rooms) {
            room.print();
        }
    }
}
// FILE_END
// FILE: Room.java
import java.util.*;

public class Room {
    private String name;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addReservation(String reservedBy, int startTime, int duration) {
        reservations.add(new Reservation( reservedBy, startTime, duration));
    }

    public void print() {
        IO.print(" ");
        IO.println(name);
        if (reservations.isEmpty()) {
            IO.print("  ");
            IO.println("No reservations.");
        } else {
            for (Reservation reservation : reservations) {
                reservation.print();
            }
        }
    }
}
// FILE_END
// FILE: Reservation.java
public class Reservation {
    private String reservedBy;
    private int startTime;
    private int duration;

    public Reservation(String reservedBy, int startTime, int duration) {
        this.reservedBy = reservedBy;
        this.startTime = startTime;
        this.duration = duration;
    }

    public void print() {
        IO.print("  ");
        IO.println("From " + startTime + ":00 to " + (startTime + duration)
            + ":00, reserved by " + reservedBy);
    }
}
// FILE_END
// FILE: main.java
void main() {
    Building agora = new Building("Agora");
    agora.addRoom("Auditorium 1");
    agora.addRoom("AgFinland");
    agora.addRoom("AgFinland"); // Fails.

    agora.addReservation("AgFinland", "Maija", 8, 2);
    agora.addReservation("AgFinland", "Matti", 10, 2);
    agora.addReservation("Auditorium 5", "Maija", 13, 1); // Fails.

    IO.println();
    agora.print();
}
// FILE_END
```

We can now use the `Building` class without needing to know anything about how rooms or reservations work internally.
Similarly, the `Building` class does not depend directly on how the `Room` class stores reservation information.

At this point, we are still missing some of the most important tools of object-oriented programming: *inheritance*, *polymorphism*, and *interfaces*. We will explore these concepts in the next chapter.
