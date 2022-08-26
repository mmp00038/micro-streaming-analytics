package com.msa.microstreaminganalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticsListener {
    private static final Logger log = LoggerFactory.getLogger(StatisticsListener.class);

    @RabbitListener(queues = MicrostreaminganalyticsApplication.DEFAULT_PARSING_QUEUE)
    public void consumeDefaultMessage(final Message message) {
        log.info("Received message, statistics are: {}", message.toString());
    }
}
