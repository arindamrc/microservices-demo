/**
 * 
 */
package com.adex.filterservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.adex.filterservice.service.UABlacklistService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author arc
 *
 */
@RestController
@Slf4j
public class UABlacklistController {
	
	private final UABlacklistService blacklistService;
	
	@Autowired
	public UABlacklistController(UABlacklistService blacklistService) {
		this.blacklistService = blacklistService;
	}
	
	@GetMapping("/blacklist/{agent}")
	public ResponseEntity<BooleanResponse> blacklistAgent(@PathVariable("agent") final String userAgent) {
		BooleanResponse response = null;
		try {
			response = new BooleanResponse(blacklistService.blacklistUA(userAgent));
		} catch (Exception e) {
			log.error("Unable to process IP: {}; {}", userAgent, e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/whitelist/{agent}")
	public ResponseEntity<BooleanResponse> whitelistAgent(@PathVariable("agent") final String userAgent) {
		BooleanResponse response = null;
		try {
			response = new BooleanResponse(blacklistService.whitelistUA(userAgent));
		} catch (Exception e) {
			log.error("Unable to process IP: {}; {}", userAgent, e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok().body(response);
	}
}
