/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.EventListener;

/**
 * @author lalbrecht
 * 
 */
public interface IArrayListEventListener extends EventListener {

	void arrayListChanged(ArrayListEvent e);

	void arrayListCleared(ArrayListEvent e);

	void arrayListItemAdded(ArrayListEvent e);

	void arrayListItemRemoved(ArrayListEvent e);

}
