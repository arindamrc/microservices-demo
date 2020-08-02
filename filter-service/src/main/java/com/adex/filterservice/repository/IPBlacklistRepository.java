/**
 * 
 */
package com.adex.filterservice.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.adex.filterservice.domain.IPBlacklist;

/**
 * Database manipulator for {@link IPBlacklist}.
 * 
 * @author arc
 *
 */
public interface IPBlacklistRepository extends CrudRepository<IPBlacklist, BigInteger>{

}
