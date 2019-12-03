package hu.pazsitz.tools.java.util.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

import hu.pazsitz.tools.google.collect.MultimapCollector;
import hu.pazsitz.tools.java.util.FilterableList;

public class Collectors {

	public static <T> Collector<T, ?, FilterableList<T>> toNonNullList() {
		return java.util.stream.Collectors.toCollection(FilterableList::new);
	}
	
	/**
	 * Returns a {@code Collector} that accumulates elements into a {@code Map}, 
	 * whose keys are the result of applying the provided mapping function 
	 * to the input elements.
	 * The values mapped by the provided values
	 * 
	 * <p>If the mapped keys contains duplicates (according to {@link Object#equals(Object)}}), 
	 * an {@code IllegalArgumentException} is thrown when the collection operation is performed.
	 * If the mapped keys may have duplicates, use {@link #toMap(Function, Function, BinaryOperator)} instead
	 * 
	 * <p>You can define the keyValue function only, the valueMapper puts 
	 * the given Object into the map values
	 * 
	 * @param keyMapper
	 * @return
	 */
	public static <T, K, U> Collector<T, ?, Map<K,U>> toMap(
			Function<? super T, ? extends K> keyMapper) {
        return java.util.stream.Collectors.toMap(keyMapper, o -> (U) o, throwingMerger(), HashMap::new);
    }
	
	/**
     * Returns a merge function, suitable for use in
     * {@link Map#merge(Object, Object, BiFunction) Map.merge()} or
     * {@link #toMap(Function, Function, BinaryOperator) toMap()}, which always
     * throws {@code IllegalStateException}.  This can be used to enforce the
     * assumption that the elements being collected are distinct.
     *
     * @param <T> the type of input arguments to the merge function
     * @return a merge function which always throw {@code IllegalStateException}
     */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }
	
	/**
	 * Returns a merge function, suitable for use in
	 * {@link Map#merge(Object, Object, BiFunction) Map.merge()} or
	 * {@link #toMap(Function, Function, BinaryOperator) toMap()}, which uses a pick (first) one strategy on identical ones. 
	 * This can be used to enforce the assumption that the elements being collected are distinct.
	 *
	 * @param <T> the type of input arguments to the merge function
	 * @return a merge function which picks the first item
	 */
	private static <T> BinaryOperator<T> pickFirstMerger() {
		return (u, v) -> u;
	}
	
	/**
	 * Returns a merge function, suitable for use in
	 * {@link Map#merge(Object, Object, BiFunction) Map.merge()} or
	 * {@link #toMap(Function, Function, BinaryOperator) toMap()}, which uses a pick (last) one strategy on identical ones. 
	 * This can be used to enforce the assumption that the elements being collected are distinct.
	 *
	 * @param <T> the type of input arguments to the merge function
	 * @return a merge function which picks the latest item
	 */
	private static <T> BinaryOperator<T> pickLastMerger() {
		return (u, v) ->  v;
	}
	
	public static <T, K, V> MultimapCollector<T, ?, Map<K, V>> toMultiMap(
			Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends V> valueMapper) {
		return new MultimapCollector(keyMapper, valueMapper);
	}
}
