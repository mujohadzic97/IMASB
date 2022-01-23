package com.amteam.requestmicroservice;


import com.amteam.requestmicroservice.Models.RequestServiceReceiver;
import com.amteam.requestmicroservice.entities.ClassRooms;
import com.amteam.requestmicroservice.entities.ClientCourse;
import com.amteam.requestmicroservice.entities.Course;
import com.amteam.requestmicroservice.entities.Instruction;
import com.amteam.requestmicroservice.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.time.OffsetDateTime;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;



import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;



@EnableDiscoveryClient
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class RequestmicroserviceApplication {

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

   /*@Bean
    SimpleMessageListenerContainer container1(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName1);
        container.setMessageListener(listenerAdapter);
        return container;
    }*/

    @Bean
    SimpleMessageListenerContainer container2(ConnectionFactory connectionFactory,
                                              MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName2);
        container.setMessageListener(listenerAdapter);
        return container;
    }

   @Bean
    MessageListenerAdapter listenerAdapter(RequestServiceReceiver requestServiceReceiver) {
        return new MessageListenerAdapter(requestServiceReceiver, "receive");
    }


    private static final Logger logger = LoggerFactory.getLogger(RequestmicroserviceApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(RequestmicroserviceApplication.class, args);
    }

    @Bean
    public CommandLineRunner Inicijalizacija(ClassRoomRepository classRoomRepository, InstrukcijaReposiotry instrukcijaReposiotry, CourseRepository courseRepository, ClientCourseRepository clientCourseRepository) {
        return (args -> {
            instrukcijaReposiotry.save(new Instruction(new Long(2), OffsetDateTime.now(), 1,  1L, 1L));
            instrukcijaReposiotry.save(new Instruction(new Long(1), OffsetDateTime.now(), 2,  1L, 1L));
            instrukcijaReposiotry.save(new Instruction(new Long(3), OffsetDateTime.now(), 3,  1L, 2L));
            instrukcijaReposiotry.save(new Instruction(new Long(3), OffsetDateTime.now(), 2,  1L, 2L));
            instrukcijaReposiotry.save(new Instruction(new Long(3), OffsetDateTime.now(), 2,  2L, 1L));
            instrukcijaReposiotry.save(new Instruction(new Long(3), OffsetDateTime.now(), 2,  2L, 1L));
            instrukcijaReposiotry.save(new Instruction(new Long(3), OffsetDateTime.now(), 1,  2L, 1L));
            instrukcijaReposiotry.save(new Instruction(new Long(2), OffsetDateTime.now(), 2,  3L, 2L));
            instrukcijaReposiotry.save(new Instruction(new Long(1), OffsetDateTime.now(), 1,  3L, 2L));
            instrukcijaReposiotry.save(new Instruction(new Long(3), OffsetDateTime.now(), 2,  4L, 2L));
            instrukcijaReposiotry.save(new Instruction(new Long(1), OffsetDateTime.now(), 2,  5L, 1L));
            instrukcijaReposiotry.save(new Instruction(new Long(1), OffsetDateTime.now(), 2,  6L, 3L));

            Course course1 = new Course(1L,  1L,"online",
                    "meet.link.example...",20,2,"13:00","Monday/Friday",
                    OffsetDateTime.now().minusDays(40).toString(), OffsetDateTime.now().minusDays(20).toString()
            );
            Course courseDB = courseRepository.save(course1);
            ClientCourse clientCourse1 = new ClientCourse(1L, courseDB.getId());
            ClientCourse clientCourse2 = new ClientCourse(2L, courseDB.getId());
            clientCourseRepository.save(clientCourse1);
            clientCourseRepository.save(clientCourse2);
            Course course2 = new Course(1L,  3L,"live",
                    "Aleja Bosne Srebrene",5,4,"17:00","Monday/Tuesday/Friday",
                    OffsetDateTime.now().minusDays(10).toString(), OffsetDateTime.now().plusDays(20).toString()
            );
            courseDB = courseRepository.save(course2);
            ClientCourse clientCourse3 = new ClientCourse(3L, courseDB.getId());
            ClientCourse clientCourse4 = new ClientCourse(2L, courseDB.getId());
            ClientCourse clientCourse5 = new ClientCourse(5L, courseDB.getId());
            ClientCourse clientCourse6 = new ClientCourse(7L, courseDB.getId());
            clientCourseRepository.save(clientCourse3);
            clientCourseRepository.save(clientCourse4);
            clientCourseRepository.save(clientCourse5);
            clientCourseRepository.save(clientCourse6);


            Course course3 = new Course(2L,  1L,"live",
                    "Zmaja od Bosne bb",10,6,"10:00","Monday/Tuesday/Friday",
                    OffsetDateTime.now().minusDays(10).toString(), OffsetDateTime.now().plusDays(20).toString()
            );
            courseDB = courseRepository.save(course3);
            ClientCourse clientCourse7 = new ClientCourse(3L, courseDB.getId());
            ClientCourse clientCourse8 = new ClientCourse(2L, courseDB.getId());
            ClientCourse clientCourse9 = new ClientCourse(5L, courseDB.getId());
            ClientCourse clientCourse10 = new ClientCourse(4L, courseDB.getId());
            ClientCourse clientCourse11 = new ClientCourse(6L, courseDB.getId());
            ClientCourse clientCourse12 = new ClientCourse(1L, courseDB.getId());
            clientCourseRepository.save(clientCourse7);
            clientCourseRepository.save(clientCourse8);
            clientCourseRepository.save(clientCourse9);
            clientCourseRepository.save(clientCourse10);
            clientCourseRepository.save(clientCourse11);
            clientCourseRepository.save(clientCourse12);
            ClassRooms classRooms = new ClassRooms("Zmaja od Bosne bb", 10, "200KM",true);
            classRoomRepository.save(classRooms);
            classRoomRepository.save(new ClassRooms("Aleja Bosne Srebrene", 5, "100KM",true));
            classRoomRepository.save(new ClassRooms("Zmaja od Bosne bb", 20, "250KM",false));
            classRoomRepository.save(new ClassRooms("Hamdije Kreševljakovića bb", 13, "130KM",false));

        });
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ObjectMapper mapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        return mapper;
    }
}

