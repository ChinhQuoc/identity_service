package com.example.identity_service.configuation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.identity_service.service.AuthenticationService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigCorsTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CustomeJwtDecoder customeJwtDecoder;

	@MockitoBean
	private AuthenticationService authenticationService;

	@Test
	void optionsPreflightForPublicEndpointIsAllowed() throws Exception {
		mockMvc.perform(options("/identity/auth/token")
				.header(HttpHeaders.ORIGIN, "http://localhost:3000")
				.header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
				.header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "content-type"))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000"));
	}
}
