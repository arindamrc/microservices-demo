/**
 * 
 */
package com.adex.filterservice.domain;

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
 * The blacklisted User Agents.
 * 
 * @author arc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UA_BLACKLIST")
@ApiModel(value = "UA", description = "User Agent")
public class UABlacklist {
	
	@Id
	@Column(name = "UA")
	@ApiModelProperty("User Agent.")
	private String ua;
}
