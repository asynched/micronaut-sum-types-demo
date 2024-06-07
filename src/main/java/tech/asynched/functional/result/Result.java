package tech.asynched.functional.result;

public sealed interface Result<T, E> permits Ok, Err {
}
