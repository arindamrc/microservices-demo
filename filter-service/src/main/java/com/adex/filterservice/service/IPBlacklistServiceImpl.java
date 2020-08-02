/**
 * 
 */
package com.adex.filterservice.service;

import java.math.BigInteger;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adex.filterservice.domain.IPBlacklist;
import com.adex.filterservice.repository.IPBlacklistRepository;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IncompatibleAddressException;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link IPBlacklistService}.
 * 
 * @author arc
 *
 */
@Service
@Slf4j
public class IPBlacklistServiceImpl implements IPBlacklistService {
	
	private final IPBlacklistRepository blacklistRepository;
	
	@Autowired
	public IPBlacklistServiceImpl(IPBlacklistRepository blacklistRepository) {
		this.blacklistRepository = blacklistRepository;
	}

	@Override
	@Transactional
	public boolean blacklistIP(String ipAddress) throws Exception {
		IPAddressString addrString = new IPAddressString(ipAddress);
		try {
			IPAddress address = addrString.toAddress();
			BigInteger ipInteger = address.getValue();
			IPBlacklist saved = blacklistRepository.save(new IPBlacklist(ipInteger));
			if (saved.getIp().equals(ipInteger)) {
				return true;
			}else {
				return false;
			}
		} catch (AddressStringException | IncompatibleAddressException e) {
			log.error("Bad IP address: {}; problem: ", ipAddress, e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean whitelistIP(String ipAddress) throws Exception {
		IPAddressString addrString = new IPAddressString(ipAddress);
		try {
			IPAddress address = addrString.toAddress();
			BigInteger ipInteger = address.getValue();
			blacklistRepository.delete(new IPBlacklist(ipInteger));
			Optional<IPBlacklist> ipOptional = blacklistRepository.findById(ipInteger);
			return !(ipOptional.isPresent());
		} catch (AddressStringException | IncompatibleAddressException e) {
			log.error("Bad IP address: {}; problem: ", ipAddress, e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean isBlacklisted(String ipAddress) throws AddressStringException, IncompatibleAddressException {
		IPAddressString addrString = new IPAddressString(ipAddress);
		IPAddress address = addrString.toAddress();
		BigInteger ipInteger = address.getValue();
		return blacklistRepository.findById(ipInteger).isPresent();
	}

}
