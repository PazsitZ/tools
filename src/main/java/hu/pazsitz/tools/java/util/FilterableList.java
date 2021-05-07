package hu.pazsitz.tools.java.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A List proxy to enrich the list
 * Filters the List before adding <tt>e</tt>lement to collection by decorating the addition methods
 * Any other method is simply proxying the functions to the internal list
 * It skips (silently fails on) adding invalid elements (by the default predicate)
 * @author PazsitZ
 *
 * @param <E>
 */
public class FilterableList<E> implements List<E> {
	private final static FilterAction<Boolean> filterActionGetsFalse = new FilterAction<Boolean>() {
		@Override public Boolean call() {
			return false;
		}
	};
	private final static Predicate nonNull = p -> Objects.nonNull(p);
	
	private final List<E> internalList;
	
	private final Predicate<E> predicate;
	private final FilterAction<Boolean> filterAction;

	/**
	 * Uses:
	 * internalList	simple ArrayList
	 * predicate 	nonNull
	 * filterAction	gets FALSE
	 */
	@SuppressWarnings("unchecked")
	public FilterableList() {
		this(new ArrayList<E>(), nonNull, filterActionGetsFalse);
	}
	/**
	 * Uses:
	 * predicate 	nonNull
	 * filterAction	gets FALSE
	 */
	@SuppressWarnings("unchecked")
	public FilterableList(List<E> list) {
		this(list, nonNull, filterActionGetsFalse);
	}
	/**
	 * Uses:
	 * filterAction	gets FALSE
	 */
	public FilterableList(List<E> list, Predicate<E> p) {
		this(list, p, filterActionGetsFalse);
	}
	
	/**
	 * 
	 * @param internalList
	 * @param prediate
	 * @param filterAction
	 */
	public FilterableList(List<E> list, Predicate<E> p, FilterAction<Boolean> filterAction) {
		this.internalList = list;
		this.predicate = p;
		this.filterAction = filterAction;
	}
	
	@Override
	public boolean add(E e) {
		return (predicate.test(e)) ? internalList.add(e) : filterAction.call();
	}
	@Override
	public void add(int index, E e) {
		if (predicate.test(e)) internalList.add(index, e);
	}
	/**
	 * uses {@link #add(Object)} - unoptimized
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean result = false;
		for (E e: c) {
			result &= this.add(e);
		}
		return result;
	}
	/**
	 * uses {@link #add(int, Object)} - unoptimized
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean result = false;
		int i = index;
		for (E e: c) {
			if (predicate.test(e)) {
				internalList.add(i++, e);
			} else {
				result &= filterAction.call();
			}
		}
		return result;
	}
	

	@Override
	public int size() {
		return internalList.size();
	}
	@Override
	public boolean isEmpty() {
		return internalList.isEmpty();
	}
	@Override
	public boolean contains(Object o) {
		return internalList.contains(o);
	}
	@Override
	public Iterator<E> iterator() {
		return internalList.iterator();
	}
	@Override
	public Object[] toArray() {
		return internalList.toArray();
	}
	@Override
	public <T> T[] toArray(T[] a) {
		return internalList.toArray(a);
	}
	@Override
	public boolean remove(Object o) {
		return internalList.remove(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		return internalList.containsAll(c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return internalList.removeAll(c);
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		return internalList.retainAll(c);
	}
	@Override
	public void clear() {
		internalList.clear();
	}
	@Override
	public E get(int index) {
		return internalList.get(index);
	}
	@Override
	public E set(int index, E element) {
		return internalList.set(index, element);
	}
	@Override
	public E remove(int index) {
		return internalList.remove(index);
	}
	@Override
	public int indexOf(Object o) {
		return internalList.indexOf(o);
	}
	@Override
	public int lastIndexOf(Object o) {
		return internalList.lastIndexOf(o);
	}
	@Override
	public ListIterator<E> listIterator() {
		return internalList.listIterator();
	}
	@Override
	public ListIterator<E> listIterator(int index) {
		return internalList.listIterator(index);
	}
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return internalList.subList(fromIndex, toIndex);
	}
	
	
	public interface FilterAction<V> {
		V call() throws RuntimeException;
	}
}
