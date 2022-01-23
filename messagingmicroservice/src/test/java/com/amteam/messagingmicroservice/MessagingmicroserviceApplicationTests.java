package com.amteam.messagingmicroservice;

import com.amteam.messagingmicroservice.entities.MessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessagingmicroserviceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	private Logger logger = LoggerFactory.getLogger(MessagingmicroserviceApplicationTests.class);

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void invalidMessageLength() throws Exception{
		MessageRequest messageRequest = new MessageRequest("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		MvcResult response = mockMvc.perform(post("/message-instructor/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(messageRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void invalidMessagePath() throws Exception{
		MessageRequest messageRequest = new MessageRequest("Kad su instrukcije?");
		MvcResult response = mockMvc.perform(post("/message-instructor/1/21457251")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(messageRequest)))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void messageOk() throws Exception{
		MessageRequest messageRequest = new MessageRequest("Kad su instrukcije?");
		MvcResult response = mockMvc.perform(post("/message-instructor/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(messageRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void fetchMessageList() throws Exception{
		MessageRequest messageRequest = new MessageRequest("Kad su instrukcije?");
		mockMvc.perform(post("/message-instructor/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(messageRequest)))
				.andExpect(status().is(HttpStatus.OK.value()));

		MvcResult response = mockMvc.perform(get("/messages/1/1")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void fetchMessagesLastMonth() throws Exception{
		MessageRequest messageRequest = new MessageRequest("Kad su instrukcije?");
		mockMvc.perform(post("/message-instructor/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(messageRequest)))
				.andExpect(status().is(HttpStatus.OK.value()));

		MvcResult response = mockMvc.perform(get("/messages-last-month")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
}
