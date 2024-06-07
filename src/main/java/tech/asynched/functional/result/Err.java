package tech.asynched.functional.result;

public record Err<T, E>(E err) implements Result<T, E> {
}
