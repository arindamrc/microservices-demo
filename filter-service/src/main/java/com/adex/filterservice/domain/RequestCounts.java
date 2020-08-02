/**
 * 
 */
package com.adex.filterservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Encapsulates the request count statistics.
 * A convenience class; not persisted.
 * 
 * @author arc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCounts {

	/**
	 * Count of valid requests.
	 */
	private Long valid;
	
	/**
	 * Count of invalid requests.
	 */
	private Long invalid;
	
	/**
	 * Get the total request count.
	 * 
	 * @return
	 */
	public Long getTotal() {
		return valid + invalid;
	}
}
