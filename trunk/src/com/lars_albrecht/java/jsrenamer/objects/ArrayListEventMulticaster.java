/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.Vector;

/**
 * @author lalbrecht
 * 
 */
public class ArrayListEventMulticaster implements IArrayListEventListener {

	protected Vector<IArrayListEventListener>	listener	= new Vector<IArrayListEventListener>();

	public void add(final IArrayListEventListener a) {
		if (!this.listener.contains(a)) {
			this.listener.addElement(a);
		}
	}

	@Override
	public void arrayListAddAll(final ArrayListEvent e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListAddAll(e);
		}
	}

	@Override
	public void arrayListChanged(final ArrayListEvent e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListChanged(e);
		}
	}

	@Override
	public void arrayListCleared(final ArrayListEvent e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListCleared(e);
		}
	}

	@Override
	public void arrayListItemAdded(final ArrayListEvent e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListItemAdded(e);
		}
	}

	@Override
	public void arrayListItemRemoved(final ArrayListEvent e) {
		for (int i = 0; i < this.listener.size(); i++) {
			(this.listener.elementAt(i)).arrayListItemRemoved(e);
		}
	}

	public void remove(final IArrayListEventListener l) {
		this.listener.remove(l);
	}

}
