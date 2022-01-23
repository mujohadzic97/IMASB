package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.Instruction;
import com.amteam.requestmicroservice.entities.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstrukcijaReposiotry extends CrudRepository<Instruction, Long> {
    Instruction findById(long id);
    List<Instruction> findAll();
    List<Instruction> findByInstructorId (long  instructor_id);
    List<Instruction> findByClientId (long  client_id);
    List<Instruction> findBySubjectId (long  subject_id);
    List<Instruction> findByInstructorIdAndSubjectId(long instructor_id, long subject_id);

}