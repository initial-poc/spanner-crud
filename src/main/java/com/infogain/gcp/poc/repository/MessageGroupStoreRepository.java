package com.infogain.gcp.poc.repository;

import com.infogain.gcp.poc.entity.OutboxEntity;
import com.infogain.gcp.poc.entity.PNREntity;
import org.springframework.cloud.gcp.data.spanner.repository.SpannerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageGroupStoreRepository extends SpannerRepository<PNREntity, String> {
    Optional<List<PNREntity>> findByPnrid(String pnrid);
}