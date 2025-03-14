package org.emes;

public record ParseResult<T>(
    T result,
    int matchStart,
    int matchEnd
) {

}
