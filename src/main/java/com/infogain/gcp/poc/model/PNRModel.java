package com.infogain.gcp.poc.model;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.entity.OutboxEntity;
import com.infogain.gcp.poc.entity.PNREntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PNRModel {

    private String pnrid;
    private Integer messageseq;
    private String payload;
    private String status;
    private String timestamp;

    @SneakyThrows
    public PNREntity buildEntity(){
        PNREntity pnrEntity = new PNREntity();
        pnrEntity.setPnrid(pnrid);
        pnrEntity.setMessageseq(messageseq);
        pnrEntity.setPayload(payload);
        pnrEntity.setStatus(status);
        pnrEntity.setTimestamp(Timestamp.parseTimestamp(timestamp));
        return pnrEntity;
    }

}