package hu.pazsitz.tools.java.util;

import java.util.function.Function;

public class Optional {

	public static<T> java.util.Optional<T> empty() {
        return java.util.Optional.empty();
    }
	
	public static <T> java.util.Optional<T> of(T value) {
        return java.util.Optional.of(value);
    }
	
	public static <T> java.util.Optional<T> ofNullable(T value) {
        return java.util.Optional.ofNullable(value);
    }
	
	public static <T, R> R getNullOnEmpty(T nullable, Function<T, R> supplier) {
		return Optional.getDefvalOnEmpty(nullable, supplier, null);
	}
	
	public static <T, R> R getDefvalOnEmpty(T nullable, Function<T, R> supplier, R defaultVal) {
		return java.util.Optional.ofNullable(nullable)
				.map(supplier)
				.orElse(defaultVal);
	}
}
