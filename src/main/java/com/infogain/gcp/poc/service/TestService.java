package com.infogain.gcp.poc.service;

import com.infogain.gcp.poc.entity.PNREntity;
import com.infogain.gcp.poc.model.PNRModel;
import lombok.extern.log4j.Log4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Log4j
public class TestService {

    public static void main(String[] args) {
        PNRModel  model = PNRModel.builder().pnrid("PNR123").messageseq(1).payload("pnr:{}").
                timestamp("2021-04-22T07:36:42.968Z").status("PENDING").build();

        TestService testService = new TestService();
        Flux<PNREntity> flux = testService.process(model);
        flux.subscribe(x->{
            ConnectableFlux<Object> connectableFlux = testService.release(x);
            connectableFlux.subscribe(y->log.info(y));
            connectableFlux.connect();
        });
    }

    public Flux<PNREntity> process(PNRModel pnrModel)
    {
        PNREntity pnrEntity = pnrModel.buildEntity();
        return Flux.just(pnrEntity);
    }

    public ConnectableFlux<Object> release(PNREntity pnrEntity) {
        return Flux.create(fluxSink ->{
            try {
                System.out.println("About to release...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fluxSink.next(pnrEntity);}).publish();
    }
}
