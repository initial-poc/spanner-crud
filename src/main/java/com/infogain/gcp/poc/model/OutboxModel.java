package com.infogain.gcp.poc.model;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.entity.OutboxEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutboxModel {

    private String locator;
    private Integer version;
    private String parent_pnr;
    private String created;
    private String data;
    private int status;
    private int retry_count;
    private Timestamp updated;
    private long processing_time_millis;

    @SneakyThrows
    public OutboxEntity buildEntity(){
        OutboxEntity outboxEntity = new OutboxEntity();
        outboxEntity.setData(data);
        outboxEntity.setLocator(locator);
        outboxEntity.setParentPnr(parent_pnr);
        outboxEntity.setVersion(version);
        outboxEntity.setCreated(Timestamp.parseTimestamp(created));
        //TODO: change csv to get this value.
        //outboxEntity.setUpdated(Timestamp.now());
        return outboxEntity;
    }

}