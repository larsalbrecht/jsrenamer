/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.test.old;

/**
 * @author lalbrecht
 * 
 */
public class Tupel<A, B> {

	private A	objectA	= null;
	private B	objectB	= null;

	/**
	 * @param objectA
	 * @param objectB
	 */
	public Tupel(final A objectA, final B objectB) {
		super();
		this.objectA = objectA;
		this.objectB = objectB;
	}

	/**
	 * @return the objectA
	 */
	public final A getObjectA() {
		return this.objectA;
	}

	/**
	 * @return the objectB
	 */
	public final B getObjectB() {
		return this.objectB;
	}

	/**
	 * @param objectA
	 *            the objectA to set
	 */
	public final void setObjectA(final A objectA) {
		this.objectA = objectA;
	}

	/**
	 * @param objectB
	 *            the objectB to set
	 */
	public final void setObjectB(final B objectB) {
		this.objectB = objectB;
	}

}
