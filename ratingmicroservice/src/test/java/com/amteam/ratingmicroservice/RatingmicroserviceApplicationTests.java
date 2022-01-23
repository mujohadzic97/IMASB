package com.amteam.ratingmicroservice;

import com.amteam.ratingmicroservice.entities.*;
import com.amteam.ratingmicroservice.repositories.GradeRepository;
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
class RatingmicroserviceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	private Logger logger = LoggerFactory.getLogger(RatingmicroserviceApplicationTests.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void gradeOK1() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(4,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/3/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeOK2() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(4,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeOK3() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(4,"client","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-client/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeOK4() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(4,"client","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-client/2/4/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeOK5() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(4,"client","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-client/5/2/2")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeOK6() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(4,"client","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-client/1/2/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeBadValue() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(6,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeBadParameters() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(6,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeBadParametersC() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(6,"client","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-client/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeBadInstructor() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(5,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/1242/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeBadClient() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(5,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/1/11234/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradeBadSubject() throws Exception{
		GradeRequest gradeRequest = new GradeRequest(5,"instructor","good instructions");
		MvcResult response = mockMvc.perform(post("/grade-instructor/1/1/12345")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gradeRequest)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void gradesAllOK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-all")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesSpecificOK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-specific/1/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesSpecificBadParameters() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-specific/431/23421/1321")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesInstructorOK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-instructor/1/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesInstructorNOT_OK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-instructor/1/123")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesInstructorAllOK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-instructor-all/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesInstructorAllNOT_OK1() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-instructor-all/123")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void gradesInstructorAllNOT_OK2() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-instructor-all/5")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void getTop5OK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-top5-all")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

	@Test
	void getTop5SpecificOK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-top5/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void getTop5SpecificBadSubject() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-top5/112")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void getTop5SpecificNoGrades() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-top5/3")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void getCommentsOK() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-comments/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void getCommentsBadInstructor() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-comments/123")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}
	@Test
	void getCommentsBadInstructor2() throws Exception{
		MvcResult response = mockMvc.perform(get("/grades-comments/7")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString("")))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
		logger.info(response.getResponse().getContentAsString());
		assertThat(true);
	}

}
