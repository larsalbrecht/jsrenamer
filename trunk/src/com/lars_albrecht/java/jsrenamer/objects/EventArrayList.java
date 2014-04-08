/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.ArrayList;

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
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, null, -1));
		this.arrayListMulticaster.arrayListItemAdded(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, e, this.indexOf(e)));
		return result;
	}

	public void addListener(final IArrayListEventListener listener) {
		this.arrayListMulticaster.add(listener);
	}

	@Override
	public void clear() {
		super.clear();
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, null, -1));
		this.arrayListMulticaster.arrayListCleared(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, null, -1));
	}

	@Override
	public boolean remove(final Object o) {
		final int index = this.indexOf(o);
		final boolean result = super.remove(o);
		this.arrayListMulticaster.arrayListChanged(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, null, -1));
		this.arrayListMulticaster.arrayListItemRemoved(new ArrayListEvent(this, ArrayListEvent.ARRAYLIST_CHANGED, o, index));
		return result;
	}

}
