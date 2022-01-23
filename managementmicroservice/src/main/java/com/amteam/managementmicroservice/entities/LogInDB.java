package com.amteam.managementmicroservice.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "action_log")
public class LogInDB {
    @Id
    @GeneratedValue(generator = "LogIdGenerator", strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "action_time")
    private OffsetDateTime time;
    @Column(name = "microservice_name")
    private String microserviceName;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "action_type")
    private String actionType;
    @Column(name = "path")
    private String path;
    @Column(name = "action_status")
    private String actionStatus;

    public LogInDB() {
    }

    public LogInDB(OffsetDateTime time, String microserviceName, Long userId, String actionType, String path, String actionStatus) {
        this.time = time;
        this.microserviceName = microserviceName;
        this.userId = userId;
        this.actionType = actionType;
        this.path = path;
        this.actionStatus = actionStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }
}
