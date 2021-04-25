package com.infogain.gcp.poc.entity;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.model.PNRModel;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Column;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "group_message_store")
public class PNREntity implements Comparable{

    @PrimaryKey(keyOrder = 1)
    @Column(name = "pnrid")
    private String pnrid;

    @PrimaryKey(keyOrder = 2)
    @Column(name = "messageseq")
    private Integer messageseq;

    @PrimaryKey(keyOrder = 3)
    @Column(name = "status")
    private String status;

    @Column(name = "payload")
    private String payload;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @SneakyThrows
    public PNRModel buildModel() {
        PNRModel pnrModel = new PNRModel();
        BeanUtils.copyProperties(pnrModel, this);
        return pnrModel;
    }

    @Override
    public int compareTo(Object o) {
       return this.getMessageseq().compareTo(((PNREntity)o).getMessageseq());
    }
}
