package org.emes;

import java.util.regex.Pattern;

public record PatternWithFunction<T>(Pattern pattern, T function) {

}
