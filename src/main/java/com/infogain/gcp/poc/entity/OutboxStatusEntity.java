package com.infogain.gcp.poc.entity;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.model.OutboxStatusModel;
import lombok.*;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Column;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"locator", "version","destination"})
@Table(name = "outbox_status")
public class OutboxStatusEntity {

    @PrimaryKey(keyOrder = 1)
    @Column(name = "locator")
    private String locator;

    @PrimaryKey(keyOrder = 2)
    @Column(name = "version")
    private String version;

    @PrimaryKey(keyOrder = 3)
    @Column(name = "destination")
    private String destination;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @Column(name = "instance")
    private String instance;

    @SneakyThrows
    public OutboxStatusModel buildModel(){
        OutboxStatusModel outboxStatusModel = new OutboxStatusModel();
        outboxStatusModel.setStatus(this.getStatus());
        outboxStatusModel.setCreated(this.getCreated());
        outboxStatusModel.setLocator(this.getLocator());
        outboxStatusModel.setVersion(this.getVersion());
        outboxStatusModel.setDestination(this.getDestination());
        outboxStatusModel.setInstance(this.getInstance());
        outboxStatusModel.setUpdated(this.getUpdated());
        return outboxStatusModel;
    }
}
