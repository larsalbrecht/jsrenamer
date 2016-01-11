/**
 *
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.Vector;

/**
 * @param <E>
 *
 * @author lalbrecht
 */
public class ArrayListEventMulticaster<E> implements IArrayListEventListener<E> {

	protected Vector<IArrayListEventListener<E>> listener = new Vector<IArrayListEventListener<E>>();

	public void add(final IArrayListEventListener<E> a) {
		if (!this.listener.contains(a)) {
			this.listener.addElement(a);
		}
	}

	@Override
	public void arrayListAddAll(final ArrayListEvent<E> e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListAddAll(e);
		}
	}

	@Override
	public void arrayListChanged(final ArrayListEvent<E> e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListChanged(e);
		}
	}

	@Override
	public void arrayListCleared(final ArrayListEvent<E> e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListCleared(e);
		}
	}

	@Override
	public void arrayListItemAdded(final ArrayListEvent<E> e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListItemAdded(e);
		}
	}

	@Override
	public void arrayListItemRemoved(final ArrayListEvent<E> e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListItemRemoved(e);
		}
	}

	@SuppressWarnings("unused")
	public void remove(final IArrayListEventListener<E> l) {
		this.listener.remove(l);
	}

}
