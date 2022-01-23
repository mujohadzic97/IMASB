package com.amteam.requestmicroservice.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.core.annotation.Counted;

import javax.persistence.*;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(generator = "CourseIdGenerator",strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "instructor_id")
    private Long instructorId;
    @Column(name = "subject_id")
    private Long subjectId;
    @Column(name = "course_type")
    private String courseType;//online or live
    @Column(name = "meet_link")
    private String meetLink;
    @Column(name = "max_capacity")
    private int maxCapacity;
    @Column(name = "current_capacity")
    private int currentCapacity;
    @Column(name = "time_held")
    private String timeHeld;
    @Column(name = "days_held")
    private String daysHeld;//monday, tuesday...

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC")
    @Column(name = "starting_day")
    private String startingDate;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC")
    @Column(name = "end_day")
    private String endDate;

    public Course() {
    }

    public Course(Long instructorId, Long subjectId, String courseType, String meetLink, int maxCapacity, int currentCapacity, String timeHeld, String daysHeld, String startingDate, String endDate) {
        this.instructorId = instructorId;
        this.subjectId = subjectId;
        this.courseType = courseType;
        this.meetLink = meetLink;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.timeHeld = timeHeld;
        this.daysHeld = daysHeld;
        this.startingDate = startingDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getMeetLink() {
        return meetLink;
    }

    public void setMeetLink(String meetLink) {
        this.meetLink = meetLink;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public String getTimeHeld() {
        return timeHeld;
    }

    public void setTimeHeld(String timeHeld) {
        this.timeHeld = timeHeld;
    }

    public String getDaysHeld() {
        return daysHeld;
    }

    public void setDaysHeld(String daysHeld) {
        this.daysHeld = daysHeld;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
