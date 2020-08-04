/**
 * 
 */
package com.adex.filterservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents the request statistics per user per hour.
 * 
 * @author arc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
		name = "STATS"
)
//,uniqueConstraints = @UniqueConstraint(columnNames={"STAT_CUSTOMER_ID", "STAT_TS"})
public class RequestStatistics {

	/**
	 * The statistic id.
	 */
	@Id
	@GeneratedValue
	@Column(name = "STAT_ID")
	private Long id;
	
	/**
	 * The customer id.
	 */
	@NonNull
	@Column(name = "STAT_CUSTOMER_ID")
	private Long cid;
	
	/**
	 * Time-stamp of the statistic.
	 * It is in seconds.
	 */
	@NonNull
	@Column(name = "STAT_TS")
	private Long timestamp;
	
	/**
	 * Count of valid requests within the one-hour fixed window.
	 */
	@Column(name = "STAT_VALID")
	@NotNull
	private Long validCount;
	
	/**
	 * Count of invalid requests within the one-hour fixed window.
	 */
	@Column(name = "STAT_INVALID")
	@NotNull
	private Long invalidCount;
	
}
