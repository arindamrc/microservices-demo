/**
 * 
 */
package com.adex.filterservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adex.filterservice.service.IPBlacklistService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author arc
 *
 */
@RestController
@RequestMapping("/ip")
@Slf4j
public class IPBlacklistController {

	private final IPBlacklistService blacklistService;

	@Autowired
	public IPBlacklistController(IPBlacklistService blacklistService) {
		this.blacklistService = blacklistService;
	}
	
	@GetMapping("/blacklist/{ipAddress:.+}")
	public ResponseEntity<BooleanResponse> blacklistIP(@PathVariable("ipAddress") final String ipAddress) {
		BooleanResponse response = null;
		try {
			response = new BooleanResponse(blacklistService.blacklistIP(ipAddress));
		} catch (Exception e) {
			log.error("Unable to process IP: {}; {}", ipAddress, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/whitelist/{ipAddress:.+}")
	public ResponseEntity<BooleanResponse> whitelistIP(@PathVariable("ipAddress") final String ipAddress) {
		BooleanResponse response = null;
		try {
			response = new BooleanResponse(blacklistService.whitelistIP(ipAddress));
		} catch (Exception e) {
			log.error("Unable to process IP: {}; {}", ipAddress, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
		return ResponseEntity.ok().body(response);
	}
}
