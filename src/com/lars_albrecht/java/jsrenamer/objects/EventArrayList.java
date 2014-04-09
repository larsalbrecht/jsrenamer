/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author lalbrecht
 * @param <E>
 * 
 */
public class EventArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long				serialVersionUID		= 7387572645483459257L;
	/**
	 * 
	 */
	private final ArrayListEventMulticaster	arrayListMulticaster	= new ArrayListEventMulticaster();

	@Override
	public boolean add(final E e) {
		final boolean result = super.add(e);
		final ArrayList<Object> tempList = new ArrayList<Object>();
		tempList.add(e);
		this.arrayListMulticaster.arrayListItemAdded(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_ADD, tempList, this.indexOf(e)));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, tempList, -1));
		return result;
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		final boolean result = super.addAll(c);
		final ArrayList<Object> tempList = new ArrayList<Object>();
		for (final Object o : c) {
			tempList.add(o);
		}
		this.arrayListMulticaster.arrayListAddAll(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_ADDALL, tempList, -1));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, tempList, -1));
		return result;
	}

	public void addListener(final IArrayListEventListener listener) {
		this.arrayListMulticaster.add(listener);
	}

	@Override
	public void clear() {
		super.clear();
		this.arrayListMulticaster.arrayListCleared(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CLEARED, null, -1));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, null, -1));
	}

	@Override
	public boolean remove(final Object o) {
		final int index = this.indexOf(o);
		final boolean result = super.remove(o);
		final ArrayList<Object> tempList = new ArrayList<Object>();
		tempList.add(o);
		this.arrayListMulticaster.arrayListItemRemoved(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_REMOVE, tempList, index));
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, tempList, -1));
		return result;
	}
}
