/**
 * 
 */
package com.adex.filterservice.exceptions;

/**
 * @author arc
 *
 */
public class IPBlacklistedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1760748222737933427L;
	
	public IPBlacklistedException() {
		super("IP Blacklisted");
	}

}
