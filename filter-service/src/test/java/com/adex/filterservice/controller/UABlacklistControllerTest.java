/**
 * 
 */
package com.adex.filterservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

import com.adex.filterservice.service.UABlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author arc
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UABlacklistController.class)
class UABlacklistControllerTest {
	
	@MockBean
	private UABlacklistService uaService;
	
	@Autowired
	private MockMvc mvc;
	
	private JacksonTester<BooleanResponse> booleanResponseJson;
	
	private final String testUA = "Mozilla";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.UABlacklistController#blacklistAgent(java.lang.String)}.
	 * @throws Exception 
	 * @throws  
	 */
	@Test
	void testBlacklistAgent() throws Exception {
		// given
		given(uaService.blacklistUA(testUA)).willReturn(true);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/ua/blacklist/"+testUA)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(new BooleanResponse(true)).getJson());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.UABlacklistController#whitelistAgent(java.lang.String)}.
	 */
	@Test
	void testWhitelistAgent() throws Exception {
		// given
		given(uaService.whitelistUA(testUA)).willReturn(true);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/ua/whitelist/"+testUA)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(new BooleanResponse(true)).getJson());
	}

}
