/**
 * 
 */
package com.adex.customerservice.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a broadcast-able customer deletion event.
 * 
 * @author arc
 *
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CustomerDeletionEvent implements Serializable{

	private static final long serialVersionUID = 1956558542225806771L;
	
	/**
	 * The deleted customer id.
	 */
	private final Long cid;

}
