package tech.asynched.functional.optional;

public record Some<T>(T value)  implements Optional<T> {
}
