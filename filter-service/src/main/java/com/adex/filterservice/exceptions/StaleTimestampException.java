/**
 * 
 */
package com.adex.filterservice.exceptions;

/**
 * @author arc
 *
 */
public class StaleTimestampException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5885372690369769091L;
	
	public StaleTimestampException() {
		super("Stale Timestamp");
	}

}
