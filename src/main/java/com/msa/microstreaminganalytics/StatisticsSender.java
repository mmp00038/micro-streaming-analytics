package com.msa.microstreaminganalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticsSender {

    private static final Logger log = LoggerFactory.getLogger(StatisticsSender.class);

    private final RabbitTemplate rabbitTemplate;

    public StatisticsSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStatistics() {
        Statistics statistics = CalculateStatistics.getJsonStatistics();
        rabbitTemplate.convertAndSend(MicrostreaminganalyticsApplication.EXCHANGE_NAME, MicrostreaminganalyticsApplication.ROUTING_KEY, statistics);
        log.info("Statistics sender");
        ImportJsonService.importTo("prueba", statistics);
    }
}
