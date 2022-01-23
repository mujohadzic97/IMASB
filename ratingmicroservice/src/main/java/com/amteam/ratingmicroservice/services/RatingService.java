package com.amteam.ratingmicroservice.services;

import org.springframework.http.*;
import com.amteam.ratingmicroservice.entities.*;
import com.amteam.ratingmicroservice.repositories.ClientRepository;
import com.amteam.ratingmicroservice.config.JwtTokenUtil;
import com.amteam.ratingmicroservice.repositories.InstructorRepository;
import com.amteam.ratingmicroservice.repositories.GradeRepository;
import com.amteam.ratingmicroservice.repositories.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class RatingService {
    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;

    private final GradeRepository gradeRepository;
    private final ErrorMessage errorMessage =  new ErrorMessage("Something went wrong!");
    private final ErrorMessage errorMessageInvalidParams =  new ErrorMessage("Invalid parameters!");


    public RatingService(GradeRepository gradeRepository, RestTemplate restTemplate,  @Qualifier("eurekaClient") EurekaClient eurekaClient, JwtTokenUtil jwtTokenUtil) {
        this.gradeRepository = gradeRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Instructor getInstructor(Long id, String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructor/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Rating microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Instructor> instructorResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor.class);
        return instructorResponseEntity.getBody();
    }

    public List<Instructor> getInstructors(String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructors";
        //return restTemplate.getForObject(url, List <Instructor>, Instructor.class);
/*
        ResponseEntity<Instructor[]> response =
                restTemplate.getForEntity(
                        url,
                        Instructor[].class);

 */
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Rating microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Instructor[]> instructorResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor[].class);

        Instructor[] instructors = instructorResponseEntity.getBody();
        List<Instructor> instructorList = new ArrayList<>(0);
        for(int i=0;i<instructors.length;i++){
            instructorList.add(instructors[i]);
        }
        return instructorList;
    }

    public Client getClient(Long id, String token) throws Exception {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/client/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Rating microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Client> clientResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Client.class);
        return clientResponseEntity.getBody();
    }


    public Subject getSubject(Long id, String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/subject/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Rating microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Subject> subjectResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Subject.class);
        return subjectResponseEntity.getBody();
    }

    public ResponseEntity<?> createGrade(GradeRequest gradeRequest, Long instructorId, Long clientId, Long subjectId, String token){
        try {

            TokenSubject tokenSubject = jwtTokenUtil.getTokenSubject(token);
            if(tokenSubject.getUserRole() != 2 && tokenSubject.getUserRole() != 3)
                return new ResponseEntity<>("Admin cannot grade!", HttpStatus.BAD_REQUEST);

            else if(gradeRequest.getGraded() == "instructor" && tokenSubject.getUserRole() == 2)
                return new ResponseEntity<>("Instructor cannot grade another instructor!", HttpStatus.BAD_REQUEST);

            else if(gradeRequest.getGraded() == "client" && tokenSubject.getUserRole() == 3)
                return new ResponseEntity<>("Client cannot grade another client!", HttpStatus.BAD_REQUEST);


            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructor-correctAvg";
            System.out.println("URL" + url);

            Client client;
            Instructor instructor;
            Subject subject;
            try {
                client = getClient(clientId, token);
                instructor = getInstructor(instructorId, token);
                subject = getSubject(subjectId, token);
            }catch (Exception e){
                return new ResponseEntity<>(errorMessageInvalidParams.getErrorMessage(), HttpStatus.BAD_REQUEST);
            }

            Grade grade = new Grade(gradeRequest.getComment(),gradeRequest.getGrade(),instructorId,clientId,subjectId,gradeRequest.getGraded());
            if(!grade.validateGrade()){
                return new ResponseEntity<>( new ErrorMessage("Invalid parameters"), HttpStatus.BAD_REQUEST);
            }
            Grade gradeDB = grade;
            List<Grade> gradeList = gradeRepository.findByInstructorIdAndClientIdAndSubjectId(instructorId,clientId,subjectId);


            if(gradeList.size()==0 || (gradeList.size() == 1 && !gradeList.get(0).getGraded().equals(grade.getGraded()))){
                //Grade gradeDB = gradeRepository.save(grade);
            }else if(gradeList.size() == 1){
                //gradeRepository.delete(gradeList.get(0));
                gradeDB = gradeList.get(0);
                gradeDB.setGrade(gradeRequest.getGrade());
                gradeDB.setComment(gradeRequest.getComment());
            }else{
                if(gradeList.get(0).getGraded().equals(gradeRequest.getGraded())){
                    //gradeRepository.delete(gradeList.get(0));
                    gradeDB = gradeList.get(0);
                    gradeDB.setGrade(gradeRequest.getGrade());
                    gradeDB.setComment(gradeRequest.getComment());
                }
                else{
                    //gradeRepository.delete(gradeList.get(1));
                    gradeDB = gradeList.get(1);
                    gradeDB.setGrade(gradeRequest.getGrade());
                    gradeDB.setComment(gradeRequest.getComment());
                }
            }
            gradeDB = gradeRepository.save(gradeDB);
            gradeList = gradeRepository.findByInstructorId(instructorId);
            double sum = 0;
            for(int i=0;i<gradeList.size();i++){
                sum += gradeList.get(i).getGrade();
            }
            instructor.setAvgGrade(sum/gradeList.size());

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.set("Service","Rating microservice");
            HttpEntity<Instructor> entity = new HttpEntity<>(instructor, headers);

            //restTemplate.postForEntity(url, instructor, Instructor.class);
            restTemplate.exchange(url, HttpMethod.POST, entity, Instructor.class);

            return new ResponseEntity<>(gradeDB,HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getGrades(){
        try {

            List<Grade> gradeList = gradeRepository.findAll();
            if(gradeList==null || gradeList.size()==0){
                return new ResponseEntity<>( new ErrorMessage("There are no grades!"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(gradeList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ResponseEntity<?> getGradesSpecific(Long instructorId, Long clientId, Long subjectId){
        try {
            List<Grade> gradeList = gradeRepository.findByInstructorIdAndClientIdAndSubjectId(instructorId,clientId,subjectId);
            if(gradeList==null || gradeList.size()==0){
                return new ResponseEntity<>( new ErrorMessage("Client and Instructor have no grades!"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(gradeList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ResponseEntity<?> getGradesInstructor(Long instructorId, Long subjectId){
        try {
            List<Grade> gradeList = gradeRepository.findByInstructorIdAndSubjectId(instructorId,subjectId);
            if(gradeList==null || gradeList.size()==0){
                return new ResponseEntity<>( new ErrorMessage("Instructor has no grades for this subject!"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(gradeList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ResponseEntity<?> getGradesInstructorAll(Long instructorId){
        try {
            List<Grade> gradeList = gradeRepository.findByInstructorId(instructorId);
            if(gradeList==null || gradeList.size()==0){
                return new ResponseEntity<>( new ErrorMessage("Instructor has no grades!"), HttpStatus.NOT_FOUND);
            }
            List<Grade> gradeListFinal = new ArrayList<>(0);
            for(int i=0;i<gradeList.size();i++){
                if(gradeList.get(i).getGraded().equals("instructor")){
                    gradeListFinal.add(gradeList.get(i));
                }
            }
            if(gradeListFinal==null || gradeListFinal.size()==0){
                return new ResponseEntity<>( new ErrorMessage("Instructor has no grades!"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(gradeListFinal,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> getTop5(Long subjectId, String token){
        try {

            List<Instructor> instructorList = getInstructors(token);
            try{
                getSubject(subjectId, token);
            }catch (Exception e){
                return new ResponseEntity<>(new ErrorMessage("Subject does not exist!"), HttpStatus.BAD_REQUEST);
            }
            List<Double> avgList = new ArrayList<>(0);
            if(instructorList.isEmpty() || instructorList.size() == 0){
                return new ResponseEntity<>( new ErrorMessage("Instructors have no grades!"), HttpStatus.NOT_FOUND);
            }
            for (int i=0;i<instructorList.size();i++){
                List<Grade> gradeList = gradeRepository.findByInstructorIdAndSubjectId(instructorList.get(i).getId(),subjectId);
                if(gradeList.size() == 0 || gradeList.isEmpty())
                    continue;
                double sum = 0;
                int numIterations = 0;

                for(int j=0;j<gradeList.size();j++){
                    System.out.println(gradeList.get(j).getGraded());
                    if(gradeList.get(j).getGraded().equals("instructor")) {
                        System.out.println(gradeList.get(j).getGraded());
                        sum += gradeList.get(j).getGrade();
                        System.out.println(sum);
                        numIterations++;
                    }
                }
                if(numIterations == 0){
                    avgList.add(0.0);
                }
                else {
                    avgList.add(sum / numIterations);
                }
            }

            List<Instructor> instructorList1 = new ArrayList<>(0);
            if(avgList.size()>0) {
                int n = avgList.size();
                for (int i = 1; i < n; ++i) {
                    double key = avgList.get(i);
                    Instructor key2 = instructorList.get(i);
                    int j = i - 1;

                    while (j >= 0 && avgList.get(j) > key) {
                        avgList.set(j + 1, avgList.get(j));
                        instructorList.set(j + 1, instructorList.get(j));
                        j = j - 1;
                    }
                    avgList.set(j + 1, key);
                    instructorList.set(j + 1, key2);
                }
                Collections.reverse(instructorList);


                if (instructorList.size() > 5) {
                    for (int i = 0; i < 5; i++)
                        instructorList1.add(instructorList.get(i));
                } else {
                    instructorList1 = instructorList;
                }
            }
            if(instructorList1.isEmpty() || instructorList1.size() == 0)
                return new ResponseEntity<>("No grades for given subject!",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(instructorList1,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> getTop5All(String token){
        try{


            List<Instructor> instructorList= getInstructors(token);
            List<Double> avgList = new ArrayList<>(0);
            for(int i=0;i<instructorList.size();i++){
                avgList.add(instructorList.get(i).getAvgGrade());
            }

            for (int i = 1; i < avgList.size(); ++i) {
                double key = avgList.get(i);
                Instructor key2 = instructorList.get(i);
                int j = i - 1;

                while (j >= 0 && avgList.get(j) > key) {
                    avgList.set(j+1,avgList.get(j));
                    instructorList.set(j+1,instructorList.get(j));
                    j = j - 1;
                }
                avgList.set(j+1,key);
                instructorList.set(j+1,key2);
            }
            Collections.reverse(instructorList);

            List<Instructor> instructorList1 = new ArrayList<>(0);
            if(instructorList.size() > 5){
                for(int i=0;i<5;i++)
                    instructorList1.add(instructorList.get(i));
            }
            else{
                instructorList1 = instructorList;
            }
            if(instructorList1.isEmpty() || instructorList1.size() == 0)
                return new ResponseEntity<>("No grades!",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(instructorList1,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getComments(Long instructorId, String token){
        try{
            try{
                getInstructor(instructorId, token);
            }catch (Exception e){
                return new ResponseEntity<>(new ErrorMessage("Instructor does not exist!"), HttpStatus.BAD_REQUEST);
            }
            List<Grade> gradeList= gradeRepository.findByInstructorId(instructorId);
            if(gradeList.isEmpty() || gradeList.size() == 0)
                return new ResponseEntity<>("No comments for given instructor!",HttpStatus.NOT_FOUND);
            List<String> commentList = new ArrayList<>(0);
            for(int i=0;i<gradeList.size();i++){
                if(!gradeList.get(i).getComment().isEmpty() && gradeList.get(i).getGraded().equals("instructor"))
                    commentList.add(gradeList.get(i).getComment());
            }
            if(commentList.isEmpty() || commentList.size() == 0)
                return new ResponseEntity<>("No comments for given instructor!",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(commentList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
