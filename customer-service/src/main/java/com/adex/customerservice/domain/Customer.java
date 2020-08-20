/**
 * 
 */
package com.adex.customerservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * This class represents a customer.
 * 
 * @author arc
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMERS")
@ApiModel(value = "Customer", description = "A customer's data.")
public final class Customer {
	
	
	/**
	 * The customer id.
	 */
	@Id
	@GeneratedValue
	@Column(name="CUSTOMER_ID", length = 11)
	@ApiModelProperty("Customer Id.")
	private Long id;
	
	/**
	 * The customer name.
	 */
	@NonNull
	@Column(nullable = false, length = 255, name = "CUSTOMER_NAME")
	@Size(min=1, max=255)
	@ApiModelProperty("Customer Name.")
	private String name;
	
	/**
	 * If the customer is active.
	 * Set to true by default so that 
	 * a newly added customer is active
	 * by default.
	 */
	@Column(name = "CUSTOMER_STATUS")
	@ApiModelProperty("Is active?.")
	private boolean active = true;
	
	
	/**
	 * Create a customer with the given name.
	 * Customer is active by default.
	 * 
	 * @param name
	 */
	public Customer(String name) {
		this.name = name;
		this.active = true;
	}

}
