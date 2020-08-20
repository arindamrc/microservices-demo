/**
 * 
 */
package com.adex.filterservice.controller;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adex.filterservice.service.IPBlacklistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author arc
 *
 */
@RestController
@RequestMapping("/ip")
@Produces("application/json")
@Api(value = "IP Blacklist")
@Slf4j
public class IPBlacklistController {

	private final IPBlacklistService blacklistService;

	@Autowired
	public IPBlacklistController(IPBlacklistService blacklistService) {
		this.blacklistService = blacklistService;
	}
	
	@PutMapping("/blacklist/{ipAddress:.+}")
	@ApiOperation(
			value = "Blacklist an IP address.",
		    notes = "The address must conform to IPv6 or IPv4 specifications.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 500, message = "Problem processing IP address.")
	public ResponseEntity<BooleanResponse> blacklistIP(@ApiParam("IP address") @PathVariable("ipAddress") final String ipAddress) {
		BooleanResponse response = null;
		try {
			response = new BooleanResponse(blacklistService.blacklistIP(ipAddress));
		} catch (Exception e) {
			log.error("Unable to process IP: {}; {}", ipAddress, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/whitelist/{ipAddress:.+}")
	@ApiOperation(
			value = "Whitelist an IP address.",
		    notes = "The address must conform to IPv6 or IPv4 specifications.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 500, message = "Problem processing IP address.")
	public ResponseEntity<BooleanResponse> whitelistIP(@ApiParam("IP address") @PathVariable("ipAddress") final String ipAddress) {
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
