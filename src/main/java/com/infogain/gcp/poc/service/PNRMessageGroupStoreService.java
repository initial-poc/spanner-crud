package com.infogain.gcp.poc.service;

import com.infogain.gcp.poc.entity.PNREntity;
import com.infogain.gcp.poc.model.PNRModel;
import com.infogain.gcp.poc.repository.MessageGroupStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class PNRMessageGroupStoreService {

    @Autowired
    MessageGroupStoreRepository msgGrpStoreRepository;

    @SuppressWarnings("all")
    @Transactional
    public Flux<PNREntity> process(PNRModel pnrModel)
    {
        PNREntity pnrEntity = pnrModel.buildEntity();
        pnrEntity = msgGrpStoreRepository.save(pnrEntity);
        return Flux.just(pnrEntity);
    }
}