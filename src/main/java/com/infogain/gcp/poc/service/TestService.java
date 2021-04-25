package com.infogain.gcp.poc.service;

import com.google.cloud.Timestamp;
import com.infogain.gcp.poc.entity.PNREntity;
import com.infogain.gcp.poc.model.PNRModel;
import io.netty.util.ResourceLeakException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Service
public class TestService {

    public static void main(String[] args) {
        PNRModel model = PNRModel.builder().pnrid("PNR123").messageseq(1).payload("pnr:{}").
                timestamp("2021-04-22T07:36:42.968Z").status("PENDING").build();

        List<PNREntity> pnrlist = new ArrayList<PNREntity>();

        pnrlist.add(PNREntity.builder().pnrid("PNR123").messageseq(1).payload("pnr:{}").
                timestamp(Timestamp.parseTimestamp("2021-04-22T07:36:42.968Z")).status("RELEASED").build());
        pnrlist.add(PNREntity.builder().pnrid("PNR123").messageseq(2).payload("pnr:{}").
                timestamp(Timestamp.parseTimestamp("2021-04-22T07:36:42.968Z")).status("RELEASED").build());
        pnrlist.add(PNREntity.builder().pnrid("PNR123").messageseq(3).payload("pnr:{}").
                timestamp(Timestamp.parseTimestamp("2021-04-22T07:36:42.968Z")).status("PENDING").build());
        pnrlist.add(PNREntity.builder().pnrid("PNR123").messageseq(4).payload("pnr:{}").
                timestamp(Timestamp.parseTimestamp("2021-04-22T07:36:42.968Z")).status("PENDING").build());
        pnrlist.add(PNREntity.builder().pnrid("PNR123").messageseq(5).payload("pnr:{}").
                timestamp(Timestamp.parseTimestamp("2021-04-22T07:36:42.968Z")).status("PENDING").build());

        //ConnectableFlux<Object> connectableFlux =
                release(pnrlist);
        //connectableFlux.subscribe(System.out::println);
        //connectableFlux.connect();

        /*Flux<PNREntity> flux = process(model);
        flux.log().subscribe(x->{
            ConnectableFlux<Object> connectableFlux = releaseTest(x);
            connectableFlux.log().subscribe(y->log.info(y));
            connectableFlux.connect();
        });*/
    }

    public static List<PNREntity> release(List<PNREntity> pnrlist) {
        Optional<List<PNREntity>> pnrEntityList = Optional.of(pnrlist);
        List<PNREntity> list = pnrEntityList.get();
        List<PNREntity> returnList = new ArrayList<PNREntity>();

            Optional<PNREntity> maxRelease = list.stream().filter(x -> x.getStatus().equals(("RELEASED"))).
                    max(PNREntity::compareTo);
            Optional<PNREntity> minPendingOrFailed = list.stream().filter(x -> x.getStatus().
                    equals(("PENDING")) || x.getStatus().equals(("FAILED"))).min(PNREntity::compareTo);

            if (minPendingOrFailed.isPresent()) {
                if (minPendingOrFailed.get().getMessageseq() == 1) {
                    list.stream().filter(x -> x.getStatus().
                            equals(("PENDING")) || x.getStatus().equals(("FAILED"))).map(x -> returnList.add(x));
                } else {
                    if (maxRelease.isPresent()) {
                        if (maxRelease.get().getMessageseq() + 1 == minPendingOrFailed.get().getMessageseq()) {
                                    list.stream().filter(x -> x.getStatus().
                                    equals(("PENDING")) || x.getStatus().equals(("FAILED"))).
                                            forEach(x->returnList.add(x));
                        }
                    }
                }
            }
        return returnList;
    }

    public static Flux<PNREntity> process(PNRModel pnrModel) {
        PNREntity pnrEntity = pnrModel.buildEntity();
        return Flux.just(pnrEntity);
    }

    public static ConnectableFlux<Object> releaseTest(PNREntity pnrEntity) {
        return Flux.create(fluxSink -> {
            try {
                System.out.println("About to release...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fluxSink.next(pnrEntity);
        }).publish();
    }
}
