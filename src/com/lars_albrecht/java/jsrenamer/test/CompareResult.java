/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.util.ArrayList;

/**
 * @author lalbrecht
 *
 */
public class CompareResult {

	private ArrayList<StringEx>	resultList	= null;
	private Integer				start		= null;
	private Integer				end			= null;

	public CompareResult(ArrayList<StringEx> resultList, Integer start, Integer end) {
		super();
		this.resultList = resultList;
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the resultList
	 */
	public ArrayList<StringEx> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList
	 *            the resultList to set
	 */
	public void setResultList(ArrayList<StringEx> resultList) {
		this.resultList = resultList;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Integer getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Integer end) {
		this.end = end;
	}

}
