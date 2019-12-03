package hu.pazsitz.tools.java.util.stream;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Filters {

	public static <T, R> Predicate<T> distinctBy(Function<? super T, R> uniqueAttrExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return  t -> seen.add(uniqueAttrExtractor.apply(t));
	}
}
