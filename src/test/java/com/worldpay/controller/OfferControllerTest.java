package com.worldpay.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldpay.dto.OfferDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class OfferControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void offerPostShouldReturnJsonWithOfferAndId() throws Exception {
		postOffer().andExpect(status().isCreated()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":5000}"));
		;

	}

	@Test
	public void offerPostShouldReturn422StatusAndMessageWithProductDoesNotExist() throws Exception {
		postOfferWithInvalidProductId().andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("Product does not exist"));
		;

	}

	@Test
	public void offerGetShouldReturn200StatusAndWholeOfferJsonBodyWithOfferId1() throws Exception {
		postOffer();

		mvc.perform(get("/offers/1")).andExpect(status().isOk()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":5000}"));
		;

	}

	@Test
	public void offerDeleteShouldReturn200StatusAfterOfferDeleted() throws Exception {
		postOffer();
		mvc.perform(delete("/offers/1")).andExpect(status().isOk()).andExpect(content().string("offer id: 1 deleted"));

	}

	@Test
	public void offerDeleteShouldReturn204StatusAfterInvalidOfferDeleted() throws Exception {
		mvc.perform(delete("/offers/1")).andExpect(status().isNoContent())
				.andExpect(content().string("offer id: 1 not found"));

	}

	@Test
	public void offerGetShouldReturn204StatusAfterInvalidOfferDeleted() throws Exception {
		mvc.perform(delete("/offers/1")).andExpect(status().isNoContent())
				.andExpect(content().string("offer id: 1 not found"));

	}

	@Test
	public void offerGetShouldReturn404StatusAfterOfferHasExpired() throws Exception {
		// expires after 5 seconds
		postOffer();
		// wait 1 second
		Thread.sleep(1000L);
		mvc.perform(get("/offers/1")).andExpect(status().isOk()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":5000}"));
		;
		// wait 4 seconds for it to expire
		Thread.sleep(4000L);
		// offer has now expired
		mvc.perform(get("/offers/1")).andExpect(status().isNotFound())
				.andExpect(content().string("offer id: 1 not found"));
		;

	}

	@Test
	public void offerGetShouldReturn404StatusAfterOfferHasExpiredWithLongExpiry() throws Exception {
		// expires after 20 seconds
		postOfferWithLongExpiry();
		Thread.sleep(1000L);
		mvc.perform(get("/offers/1")).andExpect(status().isOk()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":20000}"));
		;
		// wait 5 seconds, expect to still be able to retrieve offer
		Thread.sleep(5000L);
		mvc.perform(get("/offers/1")).andExpect(status().isOk()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":20000}"));
		;
		// wait 5 seconds, expect to still be able to retrieve offer
		Thread.sleep(5000L);
		mvc.perform(get("/offers/1")).andExpect(status().isOk()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":20000}"));
		;
		// wait 5 seconds, expect to still be able to retrieve offer
		Thread.sleep(5000L);
		mvc.perform(get("/offers/1")).andExpect(status().isOk()).andExpect(content().string(
				"{\"id\":1,\"description\":\"offer description\",\"productId\":1,\"currency\":\"GBP\",\"price\":4.44,\"expiry\":20000}"));
		;
		// wait 5 seconds, offer has now expired
		Thread.sleep(5000L);
		mvc.perform(get("/offers/1")).andExpect(status().isNotFound())
				.andExpect(content().string("offer id: 1 not found"));
		;

	}

	private ResultActions postOffer() throws JsonProcessingException, Exception {
		return mvc.perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(
				objectMapper.writeValueAsString(new OfferDto(null, "offer description", 1L, "GBP", 4.44, 5000L))));

	}

	private ResultActions postOfferWithLongExpiry() throws JsonProcessingException, Exception {
		return mvc.perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(
				objectMapper.writeValueAsString(new OfferDto(null, "offer description", 1L, "GBP", 4.44, 20000L))));

	}

	private ResultActions postOfferWithInvalidProductId() throws JsonProcessingException, Exception {
		return mvc.perform(post("/offers").contentType(MediaType.APPLICATION_JSON).content(
				objectMapper.writeValueAsString(new OfferDto(null, "offer description", 8L, "GBP", 4.44, 5000L))));

	}

}