/**
 * 
 */
package com.adex.filterservice.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The blacklisted IP addresses.
 * 
 * @author arc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IP_BLACKLIST")
@ApiModel(value = "IP Address", description = "IP address data.")
public class IPBlacklist {

	@Id
	@Column(name = "IP")
	@ApiModelProperty("IP Address")
	private BigInteger ip;
}
