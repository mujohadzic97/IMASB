package com.amteam.ratingmicroservice;

import com.amteam.ratingmicroservice.entities.Client;
import com.amteam.ratingmicroservice.entities.Grade;
import com.amteam.ratingmicroservice.entities.Instructor;
import com.amteam.ratingmicroservice.entities.Subject;
import com.amteam.ratingmicroservice.repositories.ClientRepository;
import com.amteam.ratingmicroservice.repositories.InstructorRepository;
import com.amteam.ratingmicroservice.repositories.SubjectRepository;
import com.amteam.ratingmicroservice.repositories.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@SpringBootApplication
@EnableDiscoveryClient
public class RatingmicroserviceApplication {

	private static final Logger logger = LoggerFactory.getLogger(RatingmicroserviceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RatingmicroserviceApplication.class, args);
	}
	@Bean
	public CommandLineRunner addingKlijents(ClientRepository klijentRepository, InstructorRepository instructorRepository, SubjectRepository subjectRepository, GradeRepository gradeRepository){
		return args -> {
			gradeRepository.save(new Grade("test1",5,1,1,1,"instructor"));
			gradeRepository.save(new Grade("test3",4,1,2,2,"instructor"));
			gradeRepository.save(new Grade("test4",3,1,3,2,"instructor"));
			gradeRepository.save(new Grade("test5",4,2,3,1,"instructor"));
			gradeRepository.save(new Grade("test6",3,2,4,1,"instructor"));
			gradeRepository.save(new Grade("test5",4,2,5,2,"instructor"));
			gradeRepository.save(new Grade("test6",4,2,6,2,"instructor"));
			gradeRepository.save(new Grade("test5",5,3,1,1,"instructor"));
			gradeRepository.save(new Grade("test6",2,3,2,2,"instructor"));
			gradeRepository.save(new Grade("test5",3,3,3,2,"instructor"));
			gradeRepository.save(new Grade("test5",2,4,3,2,"instructor"));
			gradeRepository.save(new Grade("test5",3,5,3,2,"instructor"));

			gradeRepository.save(new Grade("test5",5,6,1,3,"instructor"));
			gradeRepository.save(new Grade("test5",2,7,3,1,"instructor"));
			gradeRepository.save(new Grade("test5",3,7,2,2,"instructor"));

			gradeRepository.save(new Grade("test2",5,1,1,1,"client"));
			gradeRepository.save(new Grade("test2",3,2,1,1,"client"));
			gradeRepository.save(new Grade("test2",4,3,2,1,"client"));
			gradeRepository.save(new Grade("test2",3,4,3,1,"client"));
			gradeRepository.save(new Grade("test2",2,5,4,1,"client"));

		};
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
