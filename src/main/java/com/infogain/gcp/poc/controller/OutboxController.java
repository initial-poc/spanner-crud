package com.infogain.gcp.poc.controller;

import com.infogain.gcp.poc.model.OutboxModel;
import com.infogain.gcp.poc.model.PNRModel;
import com.infogain.gcp.poc.service.OutboxService;
import com.infogain.gcp.poc.service.PNRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class OutboxController {

    @Autowired
    private OutboxService outboxService;

    @Autowired
    private PNRService pnrService;

    @GetMapping(value="/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() {
        return "Ready to serve requests";
    }

    @PostMapping(value = "/api/outbox/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OutboxModel saveOutboxModel(@RequestBody OutboxModel outboxModel){
        log.info("Received Outbox Model {}",outboxModel.toString());
        return outboxService.saveOutboxModel(outboxModel);
    }

    @PostMapping(value = "/api/pnr/receive", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptPNRRecord(@RequestBody PNRModel pnrModel){
        log.info("Received PNR Model {}",pnrModel.toString());
        pnrService.accept(pnrModel);
    }

}