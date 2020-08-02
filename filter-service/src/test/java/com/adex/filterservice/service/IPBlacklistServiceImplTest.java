/**
 * 
 */
package com.adex.filterservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adex.filterservice.domain.IPBlacklist;
import com.adex.filterservice.repository.IPBlacklistRepository;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

/**
 * @author arc
 *
 */
class IPBlacklistServiceImplTest {
	
	@Mock
	private IPBlacklistRepository ipbRepository;
	
	IPBlacklistServiceImpl ipbService;
	
	private String testIP = "192.168.1.1";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ipbService = new IPBlacklistServiceImpl(ipbRepository);
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.IPBlacklistServiceImpl#blacklistIP(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	void testBlacklistIP() throws Exception {
		// given
		IPAddressString addrString = new IPAddressString(testIP);
		IPAddress address = addrString.toAddress();
		BigInteger ipInteger = address.getValue();
		IPBlacklist ipb = new IPBlacklist(ipInteger);
		given(ipbRepository.save(ipb)).willReturn(ipb);
		
		// when
		boolean response = ipbService.blacklistIP(testIP);
		
		// then
		assertThat(response).isTrue();
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.IPBlacklistServiceImpl#whitelistIP(java.lang.String)}.
	 */
	@Test
	void testWhitelistIP() throws Exception {
		// given
		IPAddressString addrString = new IPAddressString(testIP);
		IPAddress address = addrString.toAddress();
		BigInteger ipInteger = address.getValue();
		IPBlacklist ipb = new IPBlacklist(ipInteger);
		given(ipbRepository.findById(ipInteger)).willReturn(Optional.empty());
		
		// when
		boolean response = ipbService.whitelistIP(testIP);
		
		// then
		assertThat(response).isTrue();
		verify(ipbRepository).delete(ipb);
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.IPBlacklistServiceImpl#isBlacklisted(java.lang.String)}.
	 */
	@Test
	void testIsBlacklisted() throws Exception {
		// given
		IPAddressString addrString = new IPAddressString(testIP);
		IPAddress address = addrString.toAddress();
		BigInteger ipInteger = address.getValue();
		IPBlacklist ipb = new IPBlacklist(ipInteger);
		given(ipbRepository.findById(ipInteger)).willReturn(Optional.of(ipb));
		
		// when
		boolean response = ipbService.isBlacklisted(testIP);
		
		// then
		assertThat(response).isTrue();
	}

}
