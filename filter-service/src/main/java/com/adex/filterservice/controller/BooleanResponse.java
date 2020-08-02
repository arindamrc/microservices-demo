/**
 * 
 */
package com.adex.filterservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A convenience class for wrapping a boolean response from a REST controller.
 * 
 * @author arc
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooleanResponse {

	boolean response;
}
