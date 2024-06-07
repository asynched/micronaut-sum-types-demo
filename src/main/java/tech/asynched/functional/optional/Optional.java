package tech.asynched.functional.optional;

public sealed interface Optional<T> permits Some, None {
    static <T> Optional<T> fromNullable(T value) {
        return value == null ? new None<>() : new Some<>(value);
    }
}
