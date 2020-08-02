/**
 * 
 */
package com.adex.filterservice.service;

/**
 * A service for blacklisting User Agents.
 * 
 * @author arc
 *
 */
public interface UABlacklistService {

	/**
	 * Blacklist UA.
	 * 
	 * @param ua
	 * @return
	 */
	public boolean blacklistUA(String ua);
	
	/**
	 * Whitelist UA.
	 * 
	 * @param ua
	 * @return
	 */
	public boolean whitelistUA(String ua);
	
	/**
	 * Is the UA blacklisted.
	 * 
	 * @param ua
	 * @return
	 */
	public boolean isBlacklisted(String ua);
}
