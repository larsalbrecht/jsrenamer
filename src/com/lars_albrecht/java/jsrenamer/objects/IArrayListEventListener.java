/**
 *
 */
package com.lars_albrecht.java.jsrenamer.objects;

import java.util.EventListener;

/**
 * @author lalbrecht
 */
public interface IArrayListEventListener<E> extends EventListener {

	void arrayListAddAll(ArrayListEvent<E> e);

	void arrayListChanged(ArrayListEvent<E> e);

	void arrayListCleared(ArrayListEvent<E> e);

	void arrayListItemAdded(ArrayListEvent<E> e);

	void arrayListItemRemoved(ArrayListEvent<E> e);

}
