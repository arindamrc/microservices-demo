/**
 * 
 */
package com.adex.filterservice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.adex.filterservice.domain.RequestStatistics;

/**
 * JPA repository for request statistics.
 * 
 * @author arc
 *
 */
public interface RequestStatisticsRepository extends CrudRepository<RequestStatistics, Long>{
	
	@Query("SELECT rs1 FROM com.adex.filterservice.domain.RequestStatistics rs1 WHERE (rs1.cid, rs1.timestamp) IN (SELECT rs2.cid, MAX(rs2.timestamp) from com.adex.filterservice.domain.RequestStatistics rs2 GROUP BY rs2.cid) AND rs1.cid=:cid")
	public Optional<RequestStatistics> findLatestStatistic(@Param("cid") final Long cid);
	
	public List<RequestStatistics> findByCid(final Long cid);
	
	@Query("SELECT rs FROM com.adex.filterservice.domain.RequestStatistics rs WHERE rs.timestamp >= :start AND rs.timestamp < :end")
	public List<RequestStatistics> findAllInTimeRange(final Long start, final Long end);
	
	public List<RequestStatistics> deleteByCid(final Long cid);
}
