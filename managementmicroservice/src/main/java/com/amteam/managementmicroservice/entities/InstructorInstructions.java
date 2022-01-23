package com.amteam.managementmicroservice.entities;

public class InstructorInstructions {
    int numberOfScheduledInstructions;
    Long instructorID;
    Long instructionID;

    public InstructorInstructions(){}

    public InstructorInstructions(int numberOfScheduledInstructions, Long instructorID, Long instructionID){
        this.numberOfScheduledInstructions = numberOfScheduledInstructions;
        this.instructorID = instructorID;
        this.instructionID = instructionID;
    }

    public Long getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(Long instructorID) {
        this.instructorID = instructorID;
    }

    public int getNumberOfScheduledInstructions() {
        return numberOfScheduledInstructions;
    }

    public void setNumberOfScheduledInstructions(int numberOfScheduledInstructions) {
        this.numberOfScheduledInstructions = numberOfScheduledInstructions;
    }

    public void addInstruction(){
        this.numberOfScheduledInstructions++;
    }

    public void setInstructionID(Long instructionID) {
        this.instructionID = instructionID;
    }

    public Long getInstructionID() {
        return instructionID;
    }
}
