package com.adex.filterservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@ApiModel(value = "Request", description = "A typical request.")
public class Request {
	
	/**
	 * The customer id.
	 */
	@NotNull
	@ApiModelProperty("Customer Id.")
	private Long cid;
	
	/**
	 * The tag id.
	 */
	@NotNull
	@ApiModelProperty("Tag Id.")
	private Integer tid;
	
	/**
	 * The user-agent id.
	 */
	@NotEmpty
	@ApiModelProperty("User agent.")
	private String uid;
	
	/**
	 * The remote IP address.
	 */
	@NotEmpty
	@ApiModelProperty("IP address.")
	private String ip;
	
	/**
	 * The request time-stamp.
	 * It is in seconds.
	 */
	@NotNull
	@ApiModelProperty("Request Time Stamp.")
	private Long timestamp;
	
}
