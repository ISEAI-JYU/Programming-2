# Class and Object

## Learning goals

* You understand the relationship between a class and an object, and how a class serves as a template for creating objects.
* You can define your own class and its members: attributes, methods, and constructors.
* You understand the lifecycle of objects and know how to use objects as parts of a program.
* You understand how the `this` reference works in object methods.
* You know what the `static` modifier means for class members.

## Class

The first step in object-oriented programming is defining a class using the `class` keyword. A class can be thought of as a blueprint or template from which objects are created. A class specifies what data an object contains (attributes) and what it can do (methods). Attributes and methods defined in a class are also called *class members*.

Let's do a small thought experiment: consider building houses. From a single architectural blueprint, many buildings can be constructed. They would have the same structure because they are built according to the same design, but each building would still have its own state: a different owner, color, interior, and so on. The blueprint can be compared to a class in object-oriented programming, while the buildings are the objects created from it. The name of a class describes *what* an object is. Therefore, if we created a class for buildings, a suitable name would be `Building`.

Note that in Java, class names are conventionally written starting with an uppercase letter.

Let's begin by defining an empty `Building` class and gradually expand it.

```java,ignore
class Building {
    // Inside the class we define the structure
    // that objects created from this class will follow.
}
```

*Objects* (instances) are created from a class using the `new` keyword. This allocates memory for the object, selects and executes an appropriate constructor, and returns a reference to the newly created object. We store this reference in a variable so that we can access the object through it. The class name serves as the variable's type and tells the compiler what kind of object must exist at the referenced memory location.

```java,ignore
void main() {
    // The expression 'new Building()' creates an object
    // and returns a reference to it.
    // We store this reference in the variable 'building'.
    Building building = new Building();
}
```

> [!NOTE]
> A reference variable and an object are two different things. A reference variable is like an arrow that can point to an object. However, a reference variable does not have to point to anything, in which case its value is `null`.
> An object may also be created without assigning its reference to a variable. However, if there are no references pointing to it, the object becomes inaccessible and is automatically marked as garbage.
> Multiple reference variables may refer to the same object, but a single reference variable can point to only one object at a time.


## Attributes

An attribute is a variable defined inside a class but outside any methods. It represents a property, characteristic, or state of an object. Objects created from a class always contain the attributes defined in that class.
While a class defines what information objects can have, attributes store the concrete values for each individual object. Like variables in general, attributes may be primitive data types or references. The same naming conventions apply to them.
Note that variables declared inside methods are *not* attributes; they are *local variables*. Local variables are not part of an object's state.

Let's add a few attributes to our `Building` class.

```java,ignore
public class Building {
    // These variables are attributes of the object.
    // Every building has an owner and a color, but
    // the values do not have to be the same for all objects.
    private String owner;

    // An attribute can be given a default value.
    // In this case, the default color is blue.
    private String color = "blue";
}
```

Attributes differ from local variables because their visibility can be controlled using access modifiers. Local variables only exist and are visible within a method during its execution. Attributes, on the other hand, exist for the entire lifetime of the object and are visible to all members within the class.
Attributes *can* also be made visible outside the object, although this is generally considered a poor design choice. We will return to access modifiers later.


> [!NOTE]
> A local variable may have the same name as an attribute, in which case the local variable *shadows* the attribute. When an attribute and a local variable share the same name, expressions refer to the local variable by default. Even in this case, methods can still access the attribute using the `this` reference, which we will discuss shortly.

An attribute may be assigned a default value in the class definition. Objects created from that class then receive the same initial value for their corresponding attribute. If no default value is provided, the attribute's value can be set when the object is created, later through methods, or left uninitialized.

```java,ignore
public class Building {
    // Object attribute with a default value.
    private String color = "blue";

    public void print() {
        // This local variable shadows the attribute.
        String color = "red";

        // Prints "red".
        // The identifier 'color' refers to the local variable.
        IO.println(color);

        // Prints "blue".
        // The object's attribute can be accessed using 'this'.
        IO.println(this.color);
    }
}
```

## Methods

Subroutines defined inside a class are called *methods*. While an attribute represents the internal state of an object, a method can be described as the object's ability to perform some action.
One distinctive feature of Java is that all subroutines are always contained within some class. Perhaps for this reason, Java developers commonly refer to all subroutines as methods.

Method definitions do not differ syntactically from other subroutines, and the same naming conventions apply. Methods can also be overloaded. Like attributes, their visibility outside the class can be controlled using access modifiers.

As with any subroutine, a method should perform the task indicated by its name. Large tasks should generally be divided into smaller methods.

To call an object's method, we need both the object and a reference to it. Let's add a couple of simple methods to our `Building` class for manipulating its state. Methods like these are often called *accessor methods*.

```java
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    // Object method that receives a string and stores
    // it in the 'color' attribute.
    public void setColor(String color) {
        // The parameter and attribute have the same name,
        // so we use the 'this' reference.
        this.color = color;
    }

    // Object method that returns the value of the
    // 'color' attribute to the caller.
    public String getColor() {
        return this.color;
    }
}
// FILE_END
// FILE: main.java
void main() {
    // Create two buildings.
    Building building1 = new Building();
    Building building2 = new Building();

    // Use the objects' methods to change their state.
    building1.setColor("green");
    building2.setColor("white");

    // Print the colors using accessor methods.
    IO.println(building1.getColor()); // Prints "green"
    IO.println(building2.getColor()); // Prints "white"

    // Note! We cannot call an object method without an object.
    // Building.setColor("blue"); // Causes an error.
}
// FILE_END
```

We could also add similar methods for the class's other attribute. Later in this chapter, we will discuss more sensible ways of managing object state, but for now simple `get` and `set` methods are sufficient.

***

## The `this` Reference

The keyword `this` refers to the object itself. It acts as a reference to "this object" in whose context the code is currently executing.

In previous examples, we used the `this` reference to read the object's attributes:

```java
public class Building {
    private String color;

    // ...

    public String getColor() {
        return this.color;
    }
}
```

This reference is automatically available whenever an object's method is called.
When a method is invoked, `this` is automatically set to refer to the object whose method was called, and it cannot be changed. Through this reference, the method can access the correct object's state and other methods.
Inside object methods, writing `this` is only required when another member with the same name exists within the current scope, such as a local variable. Therefore, we do not have to prefix every attribute or method call with `this`; the compiler can determine it automatically if no ambiguity exists.

In some programming languages, a similar reference is called `self`.

Let's look at a few examples.

```java
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    public void setColor(String color) {
        // We use 'this' because the parameter and
        // attribute have the same name.
        this.color = color;
    }

    public String getColor() {
        // Here 'this' is optional.
        // There are no other variables named 'color'
        // in this scope.
        return this.color;
    }
}
// FILE_END
// FILE: main.java
void main() {
    Building house = new Building();

    // We call setColor through the reference 'house'.
    // Therefore, inside the method, 'this' refers
    // to the same object as 'house'.
    house.setColor("gray");

    IO.println(house.getColor());
}
// FILE_END
```

Inside methods, we can use `this` just like any other reference variable.
For example, we can pass it as a parameter to another method. This is not necessary when calling the object's own methods, but it allows us to pass a reference to the object to methods outside the class.

```java
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Object method
    public String beautify() {
        // Pass this object to a method in another class.
        return Formatter.formatNicely(this);
    }
}
// FILE_END
// FILE: Formatter.java
public class Formatter {
    public static String formatNicely(Building building) {
        return "A lovely building owned by "
                + building.getOwner()
                + ", colored "
                + building.getColor().toLowerCase() + ".";
    }
}
// FILE_END
// FILE: main.java
void main() {
    Building house = new Building();
    house.setOwner("Mary Teacher");
    house.setColor("Yellow");
    IO.println(house.beautify());
}
// FILE_END
```

***

## Constructor

A *constructor* is a special method of a class that is used to initialize the state of a new object when it is created.
The constructor's name is always the same as the class name and begins with an uppercase letter. This differs from the naming style typically used for other methods.
A constructor does not have a return type. Instead, it always returns a reference to the newly constructed object.

Every class must have at least one constructor. So far, however, we have been creating objects without defining one ourselves. This works in Java because if a class contains *no constructors at all*, the compiler automatically creates a parameterless constructor.
This automatically generated default constructor contains no statements. However, it is only generated if the class does not define any constructor of its own.

Because constructors share the same name as the class, defining multiple constructors with different parameters is a form of method overloading. The compiler chooses the appropriate constructor automatically based on the arguments provided during object creation.

Let's temporarily remove the methods from the `Building` class and examine what happens when objects are created.

```java,ignore
// FILE: Building.java
public class Building {
    private String owner;
    private String color;
}
// FILE_END
// FILE: main.java
void main() {
    // We did not define any constructor,
    // so the object is created using the
    // automatically generated default constructor.
    Building building = new Building();
}
// FILE_END
```

In this case, the object is created using the compiler-generated default constructor because no constructor has been defined.
We can also define an equivalent constructor ourselves:

```java,ignore
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    // Parameterless constructor equivalent
    // to the default constructor.
    public Building() {
        // Here we could initialize the
        // object's state if desired.
    }
}
// FILE_END
// FILE: main.java
void main() {
    // Object created using our own constructor.
    Building building = new Building();
}
// FILE_END
```

Up to this point, we have been creating objects without proper initialization. This is generally not good practice.

A newly created building currently has neither an owner nor a color. The values of these attributes are `null`.
Depending on the object and its intended use, such values *may* be acceptable. However, if a building exists, it should probably have some owner and color.

A better approach is for the object to be usable immediately after creation. We could create an
object directly in the correct staty by defining a constructor that accepts the information needed
to initialize the object's state as parameters and properly initializes its attributes.

Let's add a constructor to the `Building` class that receives the owner and color as parameters and uses them to initialize the object's attributes. This way, we do not have to set the attributes separately after the object is created.
We can also remove the parameterless constructor so that objects can no longer be created in an invalid initial state.

```java,ignore
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    public Building(String owner, String color) {
        // Initialize the object's state using the parameters.
        // Note the use of 'this', since the parameter names
        // are the same as the attribute names.
        this.owner = owner;
        this.color = color;
    }
}
// FILE_END
// FILE: main.java
void main() {
    // Two strings are provided as arguments.
    // They match the constructor we defined.
    Building building1 = new Building("University of Jyväskylä", "white");
}
// FILE_END
```

A couple of observations:
We cannot create a building using only one parameter, for example
`Building building2 = new Building("University of Jyväskylä");`
because the constructor requires two parameters.
Similarly, we cannot create a building without parameters
`Building building3 = new Building();`
because the default constructor no longer exists.

Let's add a somewhat more advanced constructor that accepts another object of the same class and copies its state into the newly created object. Such a constructor is often called a *copy constructor*.

```java,ignore
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    public Building(String owner, String color) {
        this.owner = owner;
        this.color = color;
    }

    // Constructor that copies another Building object.
    public Building(Building source) {
        this.owner = source.owner;
        this.color = source.color;
    }
}
// FILE_END
// FILE: main.java
void main() {
    Building building1 = new Building("University of Jyväskylä", "white");

    // By passing another Building object as an argument,
    // we use the copy constructor.
    // Both buildings now have the same owner and color.
    Building building2 = new Building(building1);
}
// FILE_END
```

We can also use the `this` keyword inside a constructor similarly to a method call if we want to delegate object creation to another constructor of the same class. This often helps avoid code duplication.

Let's modify the class so that one constructor uses another constructor to initialize the attributes.

```java,ignore
// FILE: Building.java
public class Building {
    private String owner;
    private String color;

    public Building(String owner, String color) {
        this.owner = owner;
        this.color = color;
    }

    public Building(Building source) {
        // Delegate construction to the constructor above.
        this(source.owner, source.color);
    }
}
// FILE_END
```

***

## Static

Class members can be defined as belonging to the **class itself** rather than to individual objects by using the `static` modifier. Such members are called *class attributes* and *class methods*.

The word *static* can be slightly misleading. In this context, it does **not** mean that the member is permanent or immutable. Throughout this material, however, we use the term *static* to refer to class members.

Attributes and methods normally belong to an object. Object attributes and methods can access the object's state.
A class attribute, on the other hand, is not part of any object's state. There is only one copy of it, shared by all objects of the class. If one object changes the value of a class attribute, the change is visible to all objects of the same class.

Similarly, class methods are shared by all objects of a class. They do not belong to any particular object and therefore cannot directly access an object's state.
Objects may call static methods of their class, but doing so does not provide access to the object's state.
Since static methods do not belong to any object, the `this` reference cannot be used inside them.

To call an **object method**, we need an object and a reference to it.
A **static class method**, however, can be called directly through the class without creating an object.
Although static methods *can* also be invoked through object references, doing so still does not grant access to any object's state.

Let's start with a class that has no static members.

```java
// FILE: Person.java
public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
// FILE_END
// FILE: main.java
void main() {
    // We cannot call an object method directly
    // through the class.
    // Person.getName();

    Person p1 = new Person("Anna", "Korhonen");
    IO.println(p1.getName());
}
// FILE_END
```

Because `getName` is an *object method*, we must first create an object. We cannot call it statically using something like `Person.getName()`.

Now let's add a *class attribute* called `adultAgeLimit`, which represents the age of adulthood for all people. We will also add a method called `isAdult` that checks whether a given age qualifies as an adult.
Notice that this method is not associated with any particular object. It simply evaluates the given age.

```java
// FILE: Person.java
public class Person {
    private String firstName;
    private String lastName;
    private static int adultAgeLimit = 18;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public static boolean isAdult(int age) {
        return age >= adultAgeLimit;
    }
}
// FILE_END
// FILE: main.java
void main() {
    Person p1 = new Person("Anna", "Korhonen");

    IO.println(p1.getName());

    // Static methods can be called through the class:
    IO.println(Person.isAdult(20)); // true
    IO.println(Person.isAdult(15)); // false

    // Static methods can also be called through an object,
    // but this is generally discouraged.
    // System.out.println(p1.isAdult(20));
}
// FILE_END
```

We now have a *class method*, `isAdult`, that can be called without creating an object.
We could call this through an object, but that's unecessary and compiler warns about it.

Static members are useful when we want functionality that does not belong to any single object but instead relates to the class as a whole.

Static members can also be accessed from object methods. For example, we can
check if person is adult

```java,ignore
// An object method can use a static variable.
public boolean isPersonAdult() {
    return this.age >= adultAgeLimit;
}
```

For clarity, it is often a good idea to use the class name even inside the class itself
`Person.adultAgeLimit`.
This makes it easier to distinguish static members from object members.

The previous example is also a warning: the adulthood limit can be modified from anywhere that has access to the `Person` class. Using this kind of global shared state is generally considered a poor design choice.

One example of a static method that we use frequently is `IO.println()`.
This is a static method of [`IO` class](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/io/IO.html)
and it is convenient because we do not need to create a separate object every time we want to print something.

<!-- ================================================== -->
## Object Lifecycle

During program execution, instances of classes are created; these instances are called *objects*.
To access an object, we create a *reference variable* for it. During compilation, Java verifies that the variable type is compatible with the object being created and then stores a reference to the newly created object in that variable.

When an object is created, Java allocates space for it in the JVM's *heap memory*. When there are no longer any references to the object, it becomes eligible for destruction.
In Java, programmers do not need to manually allocate or free memory. The memory occupied by discarded objects is eventually reclaimed by Java's automatic *garbage collector*.

Let's walk through an object's entire lifecycle using examples.
To create objects, we first need a class. We'll once again use a `Person` class and add a few simple methods for modifying the object's state.

```java
// FILE: Person.java
class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
// FILE_END
// FILE: main.java
void main() {
    // We can declare a reference variable without
    // making it refer to any object yet.
    // No object is created here.
    Person p0;

    // An object is created and its reference is
    // stored in p0.
    p0 = new Person("Mikko", "Mäkinen");

    // An object is created, but its reference is not
    // stored anywhere.
    // We can no longer access this object, so it
    // becomes eligible for garbage collection.
    new Person("Mikko", "Mäkinen");

    // Usually it is more straightforward to declare
    // the reference variable and create the object
    // in a single statement.
    Person p1 = new Person("Mikko", "Mäkinen");
    IO.println("p1: " + p1.getName());

    // We can create another object by supplying
    // values matching the constructor parameters.
    Person p2 = new Person("Anna", "Korhonen");
    IO.println("p2: " + p2.getName());

    // Multiple reference variables can point
    // to the same object. The object is not copied.
    Person p3 = p2;
    IO.println("p3: " + p3.getName());
}
// FILE_END
```

In the main program above, objects are created in several different ways while demonstrating how references behave.

Once objects have been created, we can inspect and modify their state during program execution.

```java
// FILE: main.java
void main() {
    Person p1 = new Person("Joni", "Mäkinen");
    IO.println(p1.getName());
    p1.setLastName("Korhonen");
    IO.println(p1.getName());
}
// FILE_END
// FILE: Person.java
class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
// FILE_END
```


Let's finally examine the end of an object's lifecycle: its destruction.
When an object no longer has any references pointing to it, it becomes *garbage*, meaning that Java's automatic *garbage collection* mechanism may eventually remove it from memory and reclaim the space it occupies.

```java
// FILE: main.java
void main() {
    // Create two objects.
    Person p1 = new Person("Joni", "Mäkinen");
    Person p2 = new Person("Anna", "Korhonen");

    // p1 is currently the only reference to the first object.
    // If we assign another reference to p1 or set it to null,
    // the object becomes eligible for garbage collection,
    // because no references to it remain.
    p1 = null;

    // When the method ends, all local variables created
    // inside it (p1 and p2) are destroyed.
    // If there are no other references to the objects,
    // they too become eligible for garbage collection.
}
// FILE_END
// FILE: Person.java
class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
// FILE_END
```

We will not study Java's garbage collector or memory management in great detail in this course.
For the purposes of this course, it is sufficient to understand *when an object becomes garbage and can be destroyed*. Once no references to an object exist, it becomes eligible for garbage collection, and Java may later reclaim the memory it occupies. 
If you would like to explore the topic in more detail, you can start by reading more about heap memory and memory allocation [here](https://www.geeksforgeeks.org/java/jvm-heap-area/), and about Java garbage collection from [here](https://www.geeksforgeeks.org/java/garbage-collection-in-java/), both of which provide relatively accessible introductions to the subject.