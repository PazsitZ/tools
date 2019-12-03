package hu.pazsitz.tools.google.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

public class MultimapCollector<T, K, V> implements Collector<T, Multimap<K, V>, Multimap<K, V>> {

	public final Function<T, K> keyGetter;
	public final Function<T, V> valueGetter;
	
	public MultimapCollector(Function<? super T, ? extends K> keyGetter,
            Function<? super T, ? extends V> valueGetter) {
		this.keyGetter = (Function<T, K>) keyGetter;
		this.valueGetter = (Function<T, V>) valueGetter;
	}
	
	@Override
	public Supplier<Multimap<K, V>> supplier() {
		return ArrayListMultimap::create;
	}
	@Override
	public BiConsumer<Multimap<K, V>, T> accumulator() {
		return (map, element) -> map.put(keyGetter.apply(element), valueGetter.apply(element));
	}
	@Override
	public BinaryOperator<Multimap<K, V>> combiner() {
		return (map1, map2) -> {
			map1.putAll(map2);
			return map1;
		};
	}
	@Override
	public Function<Multimap<K, V>, Multimap<K, V>> finisher() {
		return map -> map;
	}
	@Override
	public Set<Characteristics> characteristics() {
		return ImmutableSet.of(Characteristics.IDENTITY_FINISH);
	}
	
	
}
