package tech.asynched.functional.result;

public record Ok<T, E>(T data) implements Result<T, E> {
}
