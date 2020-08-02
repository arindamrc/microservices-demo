package com.adex.filterservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request that is forwarded.
 * This is not a database entity that we persist
 * in this service.
 * 
 * @author arc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
	
	/**
	 * The customer id.
	 */
	@NotNull
	private Long cid;
	
	/**
	 * The tag id.
	 */
	@NotNull
	private Integer tid;
	
	/**
	 * The user id.
	 */
	@NotEmpty
	private String uid;
	
	/**
	 * The remote IP address.
	 */
	@NotEmpty
	private String ip;
	
	/**
	 * The request time-stamp.
	 * It is in seconds.
	 */
	@NotNull
	private Long timestamp;
	
}
