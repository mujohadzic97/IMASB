package com.amteam.managementmicroservice;

import com.amteam.managementmicroservice.entities.*;
import com.amteam.managementmicroservice.repositories.AdminRepository;
import com.amteam.managementmicroservice.repositories.ClientRepository;
import com.amteam.managementmicroservice.repositories.InstructorRepository;
import com.amteam.managementmicroservice.repositories.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ManagementmicroserviceApplication {

	static final String topicExchangeName1 = "nova-instrukcija";
	static final String queueName1 = "instrukcije";

	static final String topicExchangeName2 = "dodana-instrukcija-callback";
	static final String queueName2 = "instrukcije-callback";

	@Bean
	Queue queue1() {
		return new Queue(queueName1, false);
	}
	@Bean
	Queue queue2() {
		return new Queue(queueName2, false);
	}
	@Bean
	TopicExchange exchange1() {
		return new TopicExchange(topicExchangeName1);
	}
	@Bean
	TopicExchange exchange2() {
		return new TopicExchange(topicExchangeName2);
	}
	@Bean
	Binding binding1(Queue queue1, TopicExchange exchange1) {
		return BindingBuilder.bind(queue1).to(exchange1).with("request.management");
	}
	@Bean
	Binding binding2(Queue queue2, TopicExchange exchange2) {
		return BindingBuilder.bind(queue2).to(exchange2).with("management.request");
	}

	@Bean
	SimpleMessageListenerContainer container2(ConnectionFactory connectionFactory,
											  MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName1);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(ManagementServiceReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receive");
	}

	private static final Logger logger = LoggerFactory.getLogger(ManagementmicroserviceApplication.class);


	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(ManagementmicroserviceApplication.class, args);
		Server server = ServerBuilder
				.forPort(8095)
				.addService(new HelloServiceImpl()).build();
		server.start();
		server.awaitTermination();
	}
	@Bean
	public CommandLineRunner addingClients(ClientRepository clientRepository, SubjectRepository subjectRepository,
										   InstructorRepository instructorRepository, AdminRepository adminRepository){
		return (args -> {
			Instructor instructor1 = new Instructor("Mujo","Hadzic",4,"Diplomirao sam Elektrotehniku, učesnici su zadovoljni mojim instrukcijama i kursevima","mujo","9234",2,10,8);
			Instructor instructor2 = new Instructor("Adis","Hodzic",3.75,"Strpljiv sam sa studentima koji imaju slabu koncentraciju","adis","4455",2, 7,5);
			Instructor instructor3 = new Instructor("Asja","Horozic",3.33,"Imam veliko iskustvo","asjai","1122",2,13,3);
			Instructor instructor4 = new Instructor("Irfan","Mustafic",2,"Upisao sam 9 iz Diskretne matematike","irfan","1133",2, 6,2);
			Instructor instructor5 = new Instructor("Lara","Croft",3,"Radila sam kao demonstrator na fakultetu","lara","1144",2,12,2);
			Instructor instructor6 = new Instructor("Adna","Herceglija",5,"Volim da podučavam druge","adna","6133",2, 8,1);
			Instructor instructor7 = new Instructor("Enes","Turković",2.5,"Odlično radim  sa srednjoškolcima","enes","7133",2, 14,0);

			instructorRepository.save(instructor1);
			instructorRepository.save(instructor2);
			instructorRepository.save(instructor3);
			instructorRepository.save(instructor4);
			instructorRepository.save(instructor5);
			instructorRepository.save(instructor6);
			instructorRepository.save(instructor7);

			clientRepository.save(new Client("Mustafa", "Trako", "2134","mustafa",3));
			clientRepository.save(new Client("Asim", "Haskić", "2234","asim",3));
			clientRepository.save(new Client("Tarik", "Uzunović", "2334","tarik",3));
			clientRepository.save(new Client("Alija", "Hrustanović", "2434","alija",3));
			clientRepository.save(new Client("Haris", "Efendić", "2534","haris",3));
			clientRepository.save(new Client("Benjamin", "Čukle", "2634","TestUser4",3));
			clientRepository.save(new Client("Anes", "Hyseni", "2734","TestUser5",3));
			clientRepository.save(new Client("Sani", "Karić", "2834","TestUser6",3));
			adminRepository.save(new Admin("admin","admin1234",1));
			Subject subject1 = new Subject("Matematika","Geometrija - Osnovni oblici");
			Subject subject2 = new Subject("Matematika","Diskretna Matematika - Za studente Elektrotehnike");
			Subject subject3 = new Subject("Programiranje","Java,C++,C#,C,Python");
			Subject subject4 = new Subject("Fizika","Pogodno za srednjoškolce");
			subjectRepository.save(subject1);
			subjectRepository.save(subject2);
			subjectRepository.save(subject3);
			subjectRepository.save(subject4);
			instructor1.getSubjects().add(subject1);
			instructor1.getSubjects().add(subject2);
			instructor1.getSubjects().add(subject3);
			instructor1.getSubjects().add(subject4);
			instructor2.getSubjects().add(subject3);
			instructor2.getSubjects().add(subject4);
			instructor3.getSubjects().add(subject1);
			instructor4.getSubjects().add(subject2);
			instructor5.getSubjects().add(subject2);
			instructor5.getSubjects().add(subject4);
			instructor6.getSubjects().add(subject3);
			instructor7.getSubjects().add(subject4);
			instructorRepository.save(instructor1);
			instructorRepository.save(instructor2);
			instructorRepository.save(instructor3);
			instructorRepository.save(instructor4);
			instructorRepository.save(instructor5);
			instructorRepository.save(instructor6);
			instructorRepository.save(instructor7);
		});
	}


	@Bean
	public ObjectMapper mapper(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.findAndRegisterModules();
		return mapper;
	}
}

