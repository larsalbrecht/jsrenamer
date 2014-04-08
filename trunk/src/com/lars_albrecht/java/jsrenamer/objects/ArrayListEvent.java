/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.EventObject;

/**
 * @author lalbrecht
 * 
 */
public class ArrayListEvent extends EventObject {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3379194113233811040L;
	public final static int		ARRAYLIST_CHANGED	= 20001;
	public final static int		ARRAYLIST_ADD		= 20002;
	public final static int		ARRAYLIST_REMOVE	= 20003;

	protected int				id;
	protected Object			item;
	protected int				itemIndex;

	public ArrayListEvent(final Object source, final int id, final Object item, final int itemIndex) {
		super(source);
		this.id = id;
		this.item = item;
		this.itemIndex = itemIndex;
	}

	public int getId() {
		return this.id;
	}

	/**
	 * @return the item
	 */
	public Object getItem() {
		return this.item;
	}

	/**
	 * @return the itemIndex
	 */
	public int getItemIndex() {
		return this.itemIndex;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setCollectorItem(final Object item) {
		this.item = item;
	}

}
