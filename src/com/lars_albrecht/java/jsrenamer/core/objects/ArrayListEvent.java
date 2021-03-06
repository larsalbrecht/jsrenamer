/**
 *
 */
package com.lars_albrecht.java.jsrenamer.core.objects;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * @author lalbrecht
 */
public class ArrayListEvent<E> extends EventObject {

	public final static int ARRAYLIST_CHANGED = 20001;
	public final static int ARRAYLIST_ADD     = 20002;
	public final static int ARRAYLIST_REMOVE  = 20003;
	public final static int ARRAYLIST_ADDALL  = 20004;
	public final static int ARRAYLIST_CLEARED = 20005;

	/**
	 *
	 */
	private static final long serialVersionUID = -3379194113233811040L;
	protected int          id;
	protected ArrayList<E> items;
	protected int          itemIndex;

	public ArrayListEvent(final Object source, final int id, final ArrayList<E> items, final int itemIndex) {
		super(source);
		this.id = id;
		this.items = items;
		this.itemIndex = itemIndex;
	}

	@SuppressWarnings("unused")
	public int getId() {
		return this.id;
	}

	/**
	 * @return the itemIndex
	 */
	@SuppressWarnings("unused")
	public int getItemIndex() {
		return this.itemIndex;
	}

	/**
	 * @return the items
	 */
	public ArrayList<E> getItems() {
		return this.items;
	}

	/**
	 * @param items
	 * 		the items to set
	 */
	@SuppressWarnings("unused")
	public void setCollectorItems(final ArrayList<E> items) {
		this.items = items;
	}

}
