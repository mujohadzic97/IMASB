package com.amteam.requestmicroservice.Models;

import javax.persistence.Column;
import java.time.OffsetDateTime;

public class InstructionRequest {
    private OffsetDateTime scheduledDate;
    private Integer numberOfClasses;

    public InstructionRequest(){}

    public InstructionRequest(OffsetDateTime scheduledDate, Integer numberOfClasses) {
        this.scheduledDate = scheduledDate;
        this.numberOfClasses = numberOfClasses;
    }

    public void setScheduledDate(OffsetDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public OffsetDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }
}
