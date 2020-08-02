/**
 * 
 */
package com.adex.filterservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.adex.filterservice.domain.UABlacklist;

/**
 * Database manipulator for {@link UABlacklist}.
 * 
 * @author arc
 *
 */
public interface UABlacklistRepository extends CrudRepository<UABlacklist, String>{

}
