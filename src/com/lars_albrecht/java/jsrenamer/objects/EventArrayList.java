/**
 *
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @param <E>
 *
 * @author lalbrecht
 */
public class EventArrayList<E> extends ArrayList<E> {

	/**
	 *
	 */
	private static final long                         serialVersionUID     = 7387572645483459257L;
	/**
	 *
	 */
	private final        ArrayListEventMulticaster<E> arrayListMulticaster = new ArrayListEventMulticaster<E>();

	@Override
	public boolean add(final E e) {
		final boolean      result   = super.add(e);
		final ArrayList<E> tempList = new ArrayList<E>();
		tempList.add(e);
		this.arrayListMulticaster.arrayListItemAdded(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_ADD, tempList, this.indexOf(e)));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_CHANGED, tempList, -1));
		return result;
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		final boolean      result   = super.addAll(c);
		final ArrayList<E> tempList = new ArrayList<E>();
		for (final E o : c) {
			tempList.add(o);
		}
		this.arrayListMulticaster.arrayListAddAll(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_ADDALL, tempList, -1));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_CHANGED, tempList, -1));
		return result;
	}

	public void addListener(final IArrayListEventListener<E> listener) {
		this.arrayListMulticaster.add(listener);
	}

	@Override
	public void clear() {
		super.clear();
		this.arrayListMulticaster.arrayListCleared(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_CLEARED, null, -1));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_CHANGED, null, -1));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object o) {
		final int          index    = this.indexOf(o);
		final boolean      result   = super.remove(o);
		final ArrayList<E> tempList = new ArrayList<E>();
		tempList.add((E) o);
		this.arrayListMulticaster.arrayListItemRemoved(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_REMOVE, tempList, index));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent<E>(this, ArrayListEvent.ARRAYLIST_CHANGED, tempList, -1));
		return result;
	}
}
