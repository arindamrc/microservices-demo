/**
 * 
 */
package com.adex.filterservice.controller;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adex.filterservice.domain.RequestCounts;
import com.adex.filterservice.domain.RequestStatistics;
import com.adex.filterservice.dto.Request;
import com.adex.filterservice.service.RequestStatisticsService;

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
@RequestMapping("/stats")
@Produces("application/json")
@Api(value = "RequestStats")
@Slf4j
public class RequestStatisticsController {

	private RequestStatisticsService requestStatisticsService;

	@Autowired
	public RequestStatisticsController(RequestStatisticsService requestStatisticsService) {
		this.requestStatisticsService = requestStatisticsService;
	}

	@GetMapping("/customer/{cid}")
	@ApiOperation(
			value = "Get request stats for customer by Id.",
		    notes = "The count of valid and invalid requests made by the customer.",
		    response = RequestCounts.class)
	@ApiResponse(code = 500, message = "Internal Error")
	public ResponseEntity<RequestCounts> getRequestCountForCustomer(@ApiParam("Customer Id") @PathVariable("cid") final Long cid) {
		try {
			return ResponseEntity.of(requestStatisticsService.getRequestCountForCustomer(cid));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}

	@GetMapping("/customer-day/{cid}/{date}")
	@ApiOperation(
			value = "Get request stats for customer by Id on the given date.",
		    notes = "The count of valid and invalid requests made by the customer on the given date.",
		    response = RequestCounts.class)
	@ApiResponse(code = 500, message = "Internal Error")
	public ResponseEntity<RequestCounts> getRequestCountForCustomerForDay(
			@ApiParam("Customer Id") @PathVariable("cid") final Long cid, 
			@ApiParam("Date") @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") final LocalDate date) {
		try {
			return ResponseEntity.of(requestStatisticsService.getRequestCountForCustomerForDay(cid, date));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}
	
	@GetMapping("/day/{date}")
	@ApiOperation(
			value = "Get request stats on the given date.",
		    notes = "The count of valid and invalid requests made on the given date.",
		    response = RequestCounts.class)
	@ApiResponse(code = 500, message = "Internal Error")
	public ResponseEntity<RequestCounts> getRequestCountsForDay(
			@ApiParam("Date") @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy")  final LocalDate date) {
		try {
			return ResponseEntity.of(requestStatisticsService.getRequestCountForDay(date));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}
	
	@PostMapping("/request")
	@ApiOperation(
			value = "Log new request and get back request statistics.",
		    notes = "The request with its parameters.",
		    response = RequestStatistics.class)
	@ApiResponse(code = 500, message = "Internal Error")
	public ResponseEntity<RequestStatistics> logRequest(@ApiParam("The request object") @Valid @RequestBody Request request) {
		try {
			return ResponseEntity.ok().body(requestStatisticsService.addRequest(request));
		} catch (Exception e) {
			log.error("Received exception from service: {}, with message: {}", e.getClass().toString(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
