# Sum types + Sealed classes/interfaces 🎉

Sum types are a way to define a type that can be one variant of a finite number of types. It originates from category theory, where it is called a coproduct. In programming languages, it is also known as a tagged union, variant, or discriminated union.

Some languages have a built-in syntax for sum types, like Rust, Haskell and Elm. In the example below I'll show a sum type in form of an Animal using Rust.

```rust
enum Animal {
    // Name
    Dog(String),
    // Name
    Cat(String),
    // Name, age
    Person(String, u8),
}
```

Sum types provide a form of polymorphism that binds the implementation of a given type directly to its polymorphic definition.

## Implementing in Java ☕️

In newer editions of Java, we can use the same feature using sealed interfaces (or classes). To better exemplify this, let's assume the same example as above, we would implement it like so:

```java
sealed interface Animal {}
record Dog(String name) implements Animal {}
record Cat(String name) implements  Animal {}
record Person(String name, int age) implements Animal {}
```

## Ok, but why is this useful? 🤔

Sum types are useful because you can have a limited set of types that implement an interface, and, with new editions of Java, you can even pattern match on it. Let's say you now need to implement a `speak()` method on your type. With sealed classes, you could do it like so:

```java
void speak(Animal animal) {
    switch (animal) {
        case Dog(String name) -> System.out.println(name + " says woof!");
        case Cat(String name) -> System.out.println(name + " says meow!");
        case Person(String name, int age) -> System.out.printf("Hello, my name is %s and I'm %d years old!%n", name, age);
    }
}
```

> Cool, right? 😄

## An example with Java Optional

The `Optional` type in Java comes from the same idea of sum types. It can be either `empty` or `present`. It is a simple concept but could be very powerful, Optionals allow for a more expressive way to represent an empty value, such as null, but with the added safety of needing to check whether the value is present or not.

Let's rewrite Java's `Optional` using sealed classes:

```java
sealed interface Optional<T> permits Empty, Present {}
record Empty<T>() implements Optional<T> {}
record Present<T>(T value) implements Optional<T> {}

// Usage for example
void mightHaveAnEmptyValue(Optional<String> name) {
    switch (name) {
        case Empty() -> System.out.println("No name provided, I'm sad. :(");
        case Present(String value) -> System.out.printf("Hello, %s! I'm very happe%n", value);
    }
}
```

## Going beyond generic types

Sum types can be useful in many cases, let's say have a function that might fail in a finite number of ways. We'll use the example of a database operation, let's say it can fail with these errors:

- ConnectionError - Connection to the database is dropped by the server
- QueryError - The provided query is invalid
- TimeoutError - The operation took too long to complete

This is how we could implement it using sealed classes:

```java
sealed class DatabaseError extends Exception
    permits ConnectionError, QueryError, TimeoutError {}

class ConnectionError extends DatabaseError {}
class QueryError extends DatabaseError {}
class TimeoutError extends DatabaseError {}

// With this, you could throw a generic error and handle it using pattern matching
void fails() {
    try {
        db.fail();    
    } catch (DatabaseError err) {
        switch (err) {
            case ConnectionError _ -> println("Failed to connect to the database");
            case QueryError _ -> println("Invalid query provided");
            case TimeoutError _ -> println("Operation took too long to complete");
        }
    }
}
```

> Note: This is just an example, you could handle it differently depending on your application. If you're building a web server, you might want to throw a `BadRequestException` if the query provided is not valid, for example.

## Now, let's go to a real world implementation of it. 🚀

> Example shown in code.

## Conclusion

Sum types are a powerful tool that can help you write more expressive code. They can be used to represent a limited set of types, which can be very useful in many cases. With the new editions of Java, you can now use sealed classes to achieve the same result as sum types in other languages. This can help you write more robust code and make your codebase more maintainable.

## Learn more

- [Algebraic data types](https://en.wikipedia.org/wiki/Algebraic_data_type)
- [Sealed classes](https://www.baeldung.com/java-sealed-classes-interfaces)
- [Rust enums](https://www.youtube.com/watch?v=s5S2Ed5T-dc)