package com.kunal.money_transfer_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MoneyTransferApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void testSuccessfulTransfer() throws Exception {
		mockMvc.perform(post("/v1/api/transfer")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								    "fromAccount": "A",
								    "toAccount": "B",
								    "amount": 100
								}
								"""))
				.andExpect(status().isOk());

		mockMvc.perform(get("/v1/api/accounts"))
				.andExpect(jsonPath("$.accounts.A").value(900))
				.andExpect(jsonPath("$.accounts.B").value(1100));
	}

	@Test
	void testInsufficientFunds() throws Exception {
		mockMvc.perform(post("/v1/api/transfer")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								    "fromAccount": "A",
								    "toAccount": "B",
								    "amount": 10000
								}
								"""))
				.andExpect(status().is4xxClientError());
	}
}
