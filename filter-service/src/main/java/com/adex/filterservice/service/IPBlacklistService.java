/**
 * 
 */
package com.adex.filterservice.service;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IncompatibleAddressException;

/**
 * A service for blacklisting IP addresses.
 * 
 * @author arc
 *
 */
public interface IPBlacklistService {

	/**
	 * Blacklist the IP address {@code ipAddress}.
	 * 
	 * @param ipAddress
	 * @return
	 * @throws Exception TODO
	 */
	public boolean blacklistIP(String ipAddress) throws Exception;
	
	/**
	 * Whitelist IP address {@code ipAddress}.
	 * 
	 * @param ipAddress
	 * @return
	 * @throws Exception TODO
	 */
	public boolean whitelistIP(String ipAddress) throws Exception;
	
	/**
	 * Is the IP address blacklisted?
	 * 
	 * @param ipAddress
	 * @return
	 * @throws Exception TODO
	 */
	public boolean isBlacklisted(String ipAddress) throws AddressStringException, IncompatibleAddressException;
}
