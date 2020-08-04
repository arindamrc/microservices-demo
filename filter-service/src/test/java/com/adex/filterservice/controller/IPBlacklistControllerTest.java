/**
 * 
 */
package com.adex.filterservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.adex.filterservice.service.IPBlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author arc
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(IPBlacklistController.class)
class IPBlacklistControllerTest {
	
	@MockBean
	private IPBlacklistService ipService;
	
	@Autowired
	private MockMvc mvc;
	
	private JacksonTester<BooleanResponse> booleanResponseJson;
	
	private final String testIPAddress = "192.168.12.23";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.IPBlacklistController#blacklistIP(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	void testBlacklistIP() throws Exception {
		// given
		given(ipService.blacklistIP(testIPAddress)).willReturn(true);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/ip/blacklist/"+testIPAddress)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(new BooleanResponse(true)).getJson());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.IPBlacklistController#whitelistIP(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	void testWhitelistIP() throws Exception {
		// given
		given(ipService.whitelistIP(testIPAddress)).willReturn(true);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/ip/whitelist/"+testIPAddress)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(new BooleanResponse(true)).getJson());
	}

}
