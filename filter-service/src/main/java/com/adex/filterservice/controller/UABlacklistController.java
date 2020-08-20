/**
 * 
 */
package com.adex.filterservice.controller;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adex.filterservice.service.UABlacklistService;

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
@Slf4j
@RequestMapping("/ua")
@Produces("application/json")
@Api(value = "UA")
public class UABlacklistController {
	
	private final UABlacklistService blacklistService;
	
	@Autowired
	public UABlacklistController(UABlacklistService blacklistService) {
		this.blacklistService = blacklistService;
	}
	
	@PutMapping("/blacklist/{agent}")
	@ApiOperation(
			value = "Blacklist a User Agent.",
		    notes = "A user agent name.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 500, message = "Problem processing UA name.")
	public ResponseEntity<BooleanResponse> blacklistAgent(@ApiParam("User agent name") @PathVariable("agent") final String userAgent) {
		BooleanResponse response = null;
		try {
			response = new BooleanResponse(blacklistService.blacklistUA(userAgent));
		} catch (Exception e) {
			log.error("Unable to process IP: {}; {}", userAgent, e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/whitelist/{agent}")
	@ApiOperation(
			value = "Whitelist a User Agent.",
		    notes = "A user agent name.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 500, message = "Problem processing UA name.")
	public ResponseEntity<BooleanResponse> whitelistAgent(@ApiParam("User agent name") @PathVariable("agent") final String userAgent) {
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
