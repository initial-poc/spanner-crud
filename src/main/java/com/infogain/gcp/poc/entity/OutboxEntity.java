package com.infogain.gcp.poc.entity;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.model.OutboxModel;
import lombok.*;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Column;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "outbox")
public class OutboxEntity {

    @PrimaryKey(keyOrder = 1)
    @Column(name = "locator")
    private String locator;

    @PrimaryKey(keyOrder = 2)
    @Column(name = "version")
    private Integer version;

    @Column(name = "parent_locator")
    private String parent_locator;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "data")
    private String data;

    private int status;
    private int retry_count;
    private Timestamp updated;
    private long processing_time_millis;

    @SneakyThrows
    public OutboxModel buildModel() {
        OutboxModel outboxModel = new OutboxModel();
        outboxModel.setCreated(this.getCreated().toString());
        outboxModel.setLocator(this.getLocator());
        outboxModel.setParent_locator(this.getParent_locator());
        outboxModel.setVersion(this.getVersion());
        outboxModel.setData(this.getData());
        return outboxModel;
    }
}
