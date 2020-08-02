/**
 * 
 */
package com.adex.filterservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adex.filterservice.domain.UABlacklist;
import com.adex.filterservice.repository.UABlacklistRepository;

/**
 * @author arc
 *
 */
class UABlacklistServiceImplTest {
	
	@Mock
	UABlacklistRepository uabRepository;
	
	UABlacklistServiceImpl uaService;

	private final String testUA = "Mozilla";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		uaService = new UABlacklistServiceImpl(uabRepository);
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.UABlacklistServiceImpl#blacklistUA(java.lang.String)}.
	 */
	@Test
	void testBlacklistUA() {
		// given
		UABlacklist uab = new UABlacklist(testUA);
		given(uabRepository.save(uab)).willReturn(uab);
		
		// when
		boolean response = uaService.blacklistUA(testUA);
		
		// then
		assertThat(response).isTrue();
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.UABlacklistServiceImpl#whitelistUA(java.lang.String)}.
	 */
	@Test
	void testWhitelistUA() {
		// given
		UABlacklist uab = new UABlacklist(testUA);
		given(uabRepository.findById(testUA)).willReturn(Optional.empty());
		
		// when
		boolean response = uaService.whitelistUA(testUA);
		
		// then
		assertThat(response).isTrue();
		verify(uabRepository).delete(uab);
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.UABlacklistServiceImpl#isBlacklisted(java.lang.String)}.
	 */
	@Test
	void testIsBlacklisted() {
		// given
		UABlacklist uab = new UABlacklist(testUA);
		given(uabRepository.findById(testUA)).willReturn(Optional.of(uab));
		
		// when
		boolean response = uaService.isBlacklisted(testUA);
		
		// then
		assertThat(response).isTrue();
	}

}
