/**
 * 
 */
package com.adex.filterservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adex.filterservice.domain.UABlacklist;
import com.adex.filterservice.repository.UABlacklistRepository;

/**
 * Implementation of {@link UABlacklistService}
 * 
 * @author arc
 *
 */
@Service
public class UABlacklistServiceImpl implements UABlacklistService {
	
	private final UABlacklistRepository blacklistRepository;
	
	@Autowired
	public UABlacklistServiceImpl(UABlacklistRepository blacklistRepository) {
		this.blacklistRepository = blacklistRepository;
	}

	@Override
	@Transactional
	public boolean blacklistUA(String ua) {
		UABlacklist saved = blacklistRepository.save(new UABlacklist(ua));
		if (saved.getUa().equals(ua)) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean whitelistUA(String ua) {
		blacklistRepository.delete(new UABlacklist(ua));
		if (!blacklistRepository.findById(ua).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isBlacklisted(String ua) {
		return blacklistRepository.findById(ua).isPresent();
	}

}
