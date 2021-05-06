package com.infogain.gcp.poc.model;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.entity.OutboxStatusEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"locator","version","destination"})
public class OutboxStatusModel {

    private String locator;
    private String version;
    private String destination;
    private String status;
    private Timestamp created;
    private Timestamp updated;
    private String instance;

    @SneakyThrows
    public OutboxStatusEntity buildEntity() {
        OutboxStatusEntity outboxStatusEntity = new OutboxStatusEntity();
        outboxStatusEntity.setStatus(this.getStatus());
        outboxStatusEntity.setCreated(this.getCreated());
        outboxStatusEntity.setDestination(this.getDestination());
        outboxStatusEntity.setLocator(this.getLocator());
        outboxStatusEntity.setVersion(this.getVersion());
        outboxStatusEntity.setInstance(this.getInstance());
        outboxStatusEntity.setUpdated(this.getUpdated());
        return outboxStatusEntity;
    }

}