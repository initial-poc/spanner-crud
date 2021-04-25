package com.infogain.gcp.poc.service;

import com.infogain.gcp.poc.entity.PNREntity;
import com.infogain.gcp.poc.repository.MessageGroupStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReleaseStrategyService {

    @Autowired
    MessageGroupStoreRepository msgGrpStoreRepository;

    @SuppressWarnings("all")
    @Transactional
    public ConnectableFlux<Object> release(PNREntity pnrEntity) {
        Optional<List<PNREntity>> pnrEntityList = msgGrpStoreRepository.findByPnrid(pnrEntity.getPnrid());
        List<PNREntity> list = pnrEntityList.get();

        ConnectableFlux<Object> flux =Flux.create(fluxSink -> {
            Optional<PNREntity> maxRelease = list.stream().filter(x -> x.getStatus().equals(("RELEASED"))).
                    max(PNREntity::compareTo);
            Optional<PNREntity> minPendingOrFailed = list.stream().filter(x -> x.getStatus().
                    equals(("PENDING")) || x.getStatus().equals(("FAILED"))).min(PNREntity::compareTo);

            if (minPendingOrFailed.isPresent()) {
                if (minPendingOrFailed.get().getMessageseq() == 1) {
                    list.stream().filter(x -> x.getStatus().
                            equals(("PENDING")) || x.getStatus().equals(("FAILED"))).map(x->fluxSink.next(x));
                } else {
                    if (maxRelease.isPresent()) {
                        if (maxRelease.get().getMessageseq() + 1 == minPendingOrFailed.get().getMessageseq()) {
                            list.stream().filter(x -> x.getStatus().
                                    equals(("PENDING")) || x.getStatus().equals(("FAILED"))).map(x->fluxSink.next(x));
                        }
                    }
                }
            }
        }).publish();
        return flux;
    }
}

//
// Failed?
// to message handler it is coming as stream but then from table it is processing as batch ??
