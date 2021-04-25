package com.infogain.gcp.poc.service;

import com.infogain.gcp.poc.entity.PNREntity;
import com.infogain.gcp.poc.model.PNRModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class PNRService {

    @Autowired
    PNRMessageGroupStoreService messageGroupStoreService;

    @Autowired
    ReleaseStrategyService releaseStrategyService;

    @SuppressWarnings("all")
    @Transactional
    public void accept(PNRModel pnrModel) {
        Flux<PNREntity> flux = messageGroupStoreService.process(pnrModel);
        flux.subscribe(x -> releaseStrategyService.release(x).subscribe(System.out::println));
    }

}