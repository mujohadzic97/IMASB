package com.amteam.managementmicroservice.interfaces;

import com.amteam.managementmicroservice.entities.Instructor;
import com.amteam.managementmicroservice.entities.InstructorInstructions;

public interface IManagemenrServicePublish {
    void sendInstructionAdded(Instructor inst, InstructorInstructions insrtuctorInstructions);
}

