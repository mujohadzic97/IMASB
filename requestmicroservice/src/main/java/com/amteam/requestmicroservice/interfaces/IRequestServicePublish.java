package com.amteam.requestmicroservice.interfaces;

public interface IRequestServicePublish {
    void sendInstructionAdded(int numberOfInstructionsForInstructor, Long instructorId, Long savedInstructionId);
}
