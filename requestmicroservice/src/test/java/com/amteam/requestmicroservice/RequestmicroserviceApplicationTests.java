package com.amteam.requestmicroservice;

import com.amteam.requestmicroservice.Models.InstructionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RequestmicroserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private Logger logger = LoggerFactory.getLogger(RequestmicroserviceApplicationTests.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void addInstructionInvalidDate() throws Exception{
		InstructionRequest instruction = new InstructionRequest(OffsetDateTime.MAX, 2);
		MvcResult response = mockMvc.perform(post("/instruction/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(instruction)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);	}

	@Test
	void addInstructionInvalidNumberOfClasses() throws Exception{
		InstructionRequest instruction = new InstructionRequest(OffsetDateTime.MAX, 0);
		MvcResult response = mockMvc.perform(post("/instruction/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(instruction)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void addInstructionHighNumberOfClasses() throws Exception{
		InstructionRequest instruction = new InstructionRequest(OffsetDateTime.MAX, 10);
		MvcResult response = mockMvc.perform(post("/instruction/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(instruction)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void getInstructionsByInstructorId() throws Exception{
		MvcResult response = mockMvc.perform(get("/instructions/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void getInstructionsByInstructorIdInvalidInstructorId() throws Exception{
		MvcResult response = mockMvc.perform(get("/instructions/144")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void getInstructionsByInstructorIdAndSubjectId() throws Exception{
		MvcResult response = mockMvc.perform(get("/instructions/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void getInstructionsByInstructorIdAndSubjectIdInvalidInstructorId() throws Exception{
		MvcResult response = mockMvc.perform(get("/instructions/35/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void getInstructionsByInstructorIdAndSubjectIdInvalidSubjectId() throws Exception{
		MvcResult response = mockMvc.perform(get("/instructions/35/77")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	public void getInstructionsByInstructorIdContentType() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/instructions/1"))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getInstructors() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/instructors"))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getInstructorInvalidId() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/instructor/89"))
				.andDo(print()).andExpect(status().isNotFound())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getTop5Instructors() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/instructorsTop5"))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getCommentsForInstructor() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/instructor-comments/1"))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getCommentsForInstructorInvalidInstructorId() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/instructor-comments/77"))
				.andDo(print()).andExpect(status().isNotFound())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getAllClients() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/clients"))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getClientById() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/client/3"))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}

	@Test
	public void getClientByIdInvalidClientId() throws Exception{
		MvcResult response = this.mockMvc.perform(get("/client/89"))
				.andDo(print()).andExpect(status().isInternalServerError())
				.andReturn();
		Assertions.assertEquals("application/json",
				response.getResponse().getContentType());
	}





}
