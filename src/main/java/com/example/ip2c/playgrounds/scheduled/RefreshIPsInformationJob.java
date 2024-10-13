package com.example.ip2c.playgrounds.scheduled;

import com.example.ip2c.playgrounds.dao.jpa.entities.IpAddressEntity;
import com.example.ip2c.playgrounds.dao.jpa.repos.IpAddressesRepository;
import com.example.ip2c.playgrounds.service.IpAddressInformationService;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@DisallowConcurrentExecution
@Slf4j
@RequiredArgsConstructor
public class RefreshIPsInformationJob {

    private final IpAddressesRepository ipAddressesRepository;
    private final IpAddressInformationService ipAddressInformationService;


    @Value("${refresh.batch.size}")
    private Integer batchSize;

    @Scheduled(cron = "${refresh.cron}")
    @Transactional(propagation = Propagation.REQUIRED)
    protected void refresh() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("Refresh IPs information started at {}", ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        int pageNo = 0;
        Pageable pageable = PageRequest.of(pageNo, batchSize, Sort.by("id"));
        Page<IpAddressEntity> page;

        do {
            page = ipAddressesRepository.findAll(pageable);

            for (IpAddressEntity ipAddressEntity : page.getContent()) {
                log.info("Refreshing information on {}...", ipAddressEntity.getIp());
                if (!ipAddressInformationService.refresh(ipAddressEntity.getIp())) {
                    log.warn("Refreshing information on {} failed!", ipAddressEntity.getIp());
                } else {
                    log.info("Successfully refreshed information on {}...", ipAddressEntity.getIp());
                }

            }

            pageable = PageRequest.of(++pageNo, batchSize);
        } while (page.hasNext());

        log.info("Refresh IPs information finished after {} second(s).", stopwatch.elapsed(TimeUnit.SECONDS));
    }
}
