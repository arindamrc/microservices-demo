/**
 * 
 */
package com.adex.filterservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.adex.filterservice.domain.RequestStatistics;

/**
 * @author arc
 *
 */
@DataJpaTest
class RequestStatisticsRepositoryTest {
	
	@Autowired
	private RequestStatisticsRepository rsr;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.adex.filterservice.repository.RequestStatisticsRepository#findLatestStatistic(java.lang.Long)}.
	 */
	@Test
	void testFindLatestStatistic() {
		rsr.save(RequestStatistics.builder().cid(10L).timestamp(1000L).validCount(10L).invalidCount(2L).build());
		rsr.save(RequestStatistics.builder().cid(10L).timestamp(2000L).validCount(20L).invalidCount(8L).build());
		rsr.save(RequestStatistics.builder().cid(20L).timestamp(1010L).validCount(11L).invalidCount(1L).build());
		rsr.save(RequestStatistics.builder().cid(20L).timestamp(1030L).validCount(30L).invalidCount(3L).build());
		
		Optional<RequestStatistics> statisticOptional = rsr.findLatestStatistic(10L);
		
		assertThat(statisticOptional.isPresent()).isTrue();
		assertThat(statisticOptional.get().getTimestamp()).isEqualTo(2000L);
	}

}
